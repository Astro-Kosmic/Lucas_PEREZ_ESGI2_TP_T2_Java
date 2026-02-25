package application;

import application.ports.ProductRepository;
import domain.product.ContainerProduct;
import domain.product.Product;
import domain.product.SimpleProduct;
import domain.sale.CartLine;
import domain.sale.DiscountPolicy;
import domain.sale.Receipt;
import domain.sale.ReceiptLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Cas d'usage : Vendre un ou plusieurs produits du stock (panier).
 * - Vérifie l'existence des produits
 * - Vérifie la disponibilité en stock
 * - Calcule le total du panier
 * - Applique une remise (via DiscountPolicy)
 * - Met à jour le stock
 * - Génère un ticket (Receipt)
 *
 * La règle de remise est externalisée dans {@link domain.sale.DiscountPolicy}
 * afin de séparer la logique métier du cas d'utilisation et de respecter
 * le principe Open/Closed (SOLID). Cela permet de modifier facilement
 * la stratégie de remise sans changer ce UseCase.
 *
 * @author Lucas
 * @version 2.1
 */
public class SellProductUseCase {

    private final ProductRepository productRepository;
    private final DiscountPolicy discountPolicy;

    /**
     * Constructeur par défaut (règle TP2).
     * Remise : 5% si total > 20€.
     *
     * @param productRepository Référentiel des produits (port d'accès aux données)
     */
    public SellProductUseCase(ProductRepository productRepository) {
        this(productRepository, new DiscountPolicy(20.0, 0.05));
    }

    /**
     * Constructeur avec injection de policy (plus propre / testable).
     *
     * @param productRepository Référentiel des produits
     * @param discountPolicy politique de remise
     */
    public SellProductUseCase(ProductRepository productRepository, DiscountPolicy discountPolicy) {
        this.productRepository = productRepository;
        this.discountPolicy = discountPolicy;
    }

    /**
     * Compatibilité TP1 : vend un seul produit (ancienne signature).
     *
     * @param productId Identifiant du produit à vendre
     * @param quantity  Quantité à vendre (doit être strictement positive)
     */
    public void execute(String productId, int quantity) {
        List<CartLine> lines = new ArrayList<>();
        lines.add(new CartLine(productId, quantity));
        executeCart(lines);
    }

    /**
     * TP2 : Exécute une vente de type panier (max 3 articles).
     *
     * @param cartLines lignes du panier (1 à 3)
     * @return le ticket de caisse
     * @throws IllegalArgumentException si panier invalide, produit introuvable, stock insuffisant, etc.
     */
    public Receipt executeCart(List<CartLine> cartLines) {
        if (cartLines == null || cartLines.isEmpty()) {
            throw new IllegalArgumentException("Le panier ne peut pas être vide.");
        }
        if (cartLines.size() > 3) {
            throw new IllegalArgumentException("Le panier ne peut pas contenir plus de 3 articles.");
        }

        // 1) Vérifications + calcul lignes ticket
        List<ReceiptLine> receiptLines = new ArrayList<>();
        double grossTotal = 0.0;

        // On stocke les produits à mettre à jour après avoir TOUT validé
        List<Product> productsToUpdate = new ArrayList<>();
        List<Integer> newStocks = new ArrayList<>();

        for (CartLine line : cartLines) {
            String productId = line.getProductId();
            int quantity = line.getQuantity();

            Product existing = productRepository.findById(productId);
            if (existing == null) {
                throw new IllegalArgumentException("Produit introuvable avec l'ID : " + productId);
            }

            if (existing.getStock() < quantity) {
                throw new IllegalArgumentException(
                        "Stock insuffisant pour le produit : " + productId +
                                " (stock=" + existing.getStock() + ", demandé=" + quantity + ")"
                );
            }

            receiptLines.add(new ReceiptLine(existing.getName(), quantity, existing.getPrice()));
            grossTotal += existing.getPrice() * quantity;

            productsToUpdate.add(existing);
            newStocks.add(existing.getStock() - quantity);
        }

        // 2) Remise via policy
        double discountAmount = discountPolicy.computeDiscount(grossTotal);
        double netTotal = grossTotal - discountAmount;

        // 3) Mise à jour du stock (après validation complète)
        for (int i = 0; i < productsToUpdate.size(); i++) {
            Product updated = copyWithNewStock(productsToUpdate.get(i), newStocks.get(i));
            productRepository.save(updated);
        }

        return new Receipt(receiptLines, grossTotal, discountAmount, netTotal);
    }

    /**
     * Recrée un produit du même type avec un stock mis à jour.
     * (Tes entités sont immuables côté stock, donc on reconstruit.)
     */
    private Product copyWithNewStock(Product existing, int newStock) {
        if (existing instanceof ContainerProduct cp) {
            return new ContainerProduct(
                    cp.getProductId(),
                    cp.getName(),
                    cp.getPrice(),
                    newStock,
                    cp.getContainedProduct(),
                    cp.getContainedQuantity(),
                    cp.getBrand(),
                    cp.getProductLine()
            );
        }

        // défaut : produit simple
        return new SimpleProduct(
                existing.getProductId(),
                existing.getName(),
                existing.getPrice(),
                newStock,
                existing.getBrand(),
                existing.getProductLine()
        );
    }
}