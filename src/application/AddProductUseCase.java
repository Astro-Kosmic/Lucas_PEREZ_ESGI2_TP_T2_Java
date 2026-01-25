package application;

import application.ports.ProductRepository;
import domain.catalog.Brand;
import domain.catalog.ProductLine;
import domain.product.Product;
import domain.product.SimpleProduct;

/**
 * Use case responsable de l'ajout d'un produit dans le stock.
 *
 * Si un produit avec le même identifiant existe déjà dans le référentiel, son stock est incrémenté
 * et son prix mis à jour. Le nom, la marque et la gamme d'origine sont conservés.
 *
 * Cette logique assure une unicité forte sur l'identifiant produit, en cohérence avec la gestion
 * automatique des identifiants côté infrastructure.
 *
 * Cette classe fait partie de la couche application et dépend d'un port {@link ProductRepository}.
 *
 * @author Lucas
 * @version 1.2
 */
public class AddProductUseCase {

    private final ProductRepository productRepository;

    /**
     * Construit le cas d'usage d'ajout de produit avec le dépôt spécifié.
     *
     * @param productRepository le dépôt de persistance des produits
     */
    public AddProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Ajoute un produit au stock.
     *
     * Si l'identifiant du produit existe déjà :
     *   le stock existant est incrémenté
     *   le prix est mis à jour avec la nouvelle valeur
     *   le nom, la marque et la gamme existants sont conservés
     *
     * Sinon, le produit est inséré tel quel.
     *
     * @param product le produit à enregistrer ou mettre à jour
     * @throws IllegalArgumentException si le produit est nul ou invalide
     */
    public void execute(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Le produit ne peut pas être nul.");
        }

        if (productRepository.existsById(product.getProductId())) {
            Product existing = productRepository.findById(product.getProductId());
            int newStock = existing.getStock() + product.getStock();
            double newPrice = product.getPrice();

            Product updated = new SimpleProduct(
                    existing.getProductId(),
                    existing.getName(),
                    newPrice,
                    newStock,
                    existing.getBrand(),
                    existing.getProductLine()
            );
            productRepository.save(updated);
        } else {
            productRepository.save(product);
        }
    }
}
