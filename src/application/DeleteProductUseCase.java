package application;

import application.ports.ProductRepository;
import domain.product.Product;

/**
 * Cas d'usage pour la suppression d’un produit du stock.
 *
 * Cette classe encapsule la logique métier liée à la suppression d’un produit existant.
 * Elle effectue les validations nécessaires avant d’interagir avec le dépôt de persistance.
 *
 * Ce use case appartient à la couche application et dépend du port {@link ProductRepository}.
 *
 * @author Lucas
 * @version 1.2
 */
public class DeleteProductUseCase {

    private final ProductRepository productRepository;

    /**
     * Construit le cas d’usage avec le référentiel produit injecté.
     *
     * @param productRepository le dépôt produit à utiliser pour les opérations
     */
    public DeleteProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Supprime le produit correspondant à l’identifiant spécifié.
     *
     * Avant suppression, vérifie que l’identifiant est valide et que le produit existe réellement.
     *
     * @param productId identifiant unique du produit à supprimer
     * @throws IllegalArgumentException si l’ID est nul, vide ou que le produit n’existe pas
     */
    public void execute(String productId) {
        validateProductId(productId);
        verifyExistence(productId);
        productRepository.deleteById(productId);
    }

    /**
     * Valide que l’identifiant fourni n’est ni nul ni vide.
     *
     * @param productId identifiant du produit
     * @throws IllegalArgumentException si l’ID est invalide
     */
    private void validateProductId(String productId) {
        if (productId == null || productId.trim().isEmpty()) {
            throw new IllegalArgumentException("L'identifiant du produit ne peut pas être vide.");
        }
    }

    /**
     * Vérifie que le produit ciblé existe bien dans le stock.
     *
     * @param productId identifiant du produit
     * @throws IllegalArgumentException si le produit est introuvable
     */
    private void verifyExistence(String productId) {
        if (!productRepository.existsById(productId)) {
            throw new IllegalArgumentException("Aucun produit trouvé avec l'ID : " + productId);
        }
    }
}
