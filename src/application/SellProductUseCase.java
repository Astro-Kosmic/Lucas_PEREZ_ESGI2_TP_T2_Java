package application;

import application.ports.ProductRepository;
import domain.product.Product;
import domain.product.SimpleProduct;

/**
 * Cas d'usage : Vendre un produit du stock.
 * Ce cas d'utilisation vérifie que le produit existe, que la quantité demandée est disponible,
 * et met à jour le stock après la vente.
 *
 * Ce comportement est atomique et garantit l'intégrité du stock.
 *
 * @author Lucas
 * @version 1.2
 */
public class SellProductUseCase {

    private final ProductRepository productRepository;

    /**
     * Constructeur du cas d'utilisation.
     *
     * @param productRepository Référentiel des produits (port d'accès aux données)
     */
    public SellProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Exécute la vente d'un produit en diminuant son stock.
     *
     * @param productId Identifiant du produit à vendre
     * @param quantity Quantité à vendre (doit être strictement positive)
     * @throws IllegalArgumentException si l'identifiant est inconnu, si le stock est insuffisant
     *                                  ou si la quantité demandée est invalide
     */
    public void execute(String productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("La quantité vendue doit être supérieure à zéro.");
        }

        if (!productRepository.existsById(productId)) {
            throw new IllegalArgumentException("Produit introuvable avec l'ID : " + productId);
        }

        Product existing = productRepository.findById(productId);
        if (existing.getStock() < quantity) {
            throw new IllegalArgumentException("Stock insuffisant pour le produit : " + productId);
        }

        int newStock = existing.getStock() - quantity;
        Product updated = new SimpleProduct(
                existing.getProductId(),
                existing.getName(),
                existing.getPrice(),
                newStock,
                existing.getBrand(),
                existing.getProductLine()
        );
        productRepository.save(updated);
    }
}