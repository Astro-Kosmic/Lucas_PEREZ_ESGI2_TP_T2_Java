package application.ports;

import domain.product.Product;
import java.util.List;

/**
 * Interface représentant le port d'accès aux données des produits.
 * <p>
 * Définit les opérations de persistance nécessaires pour interagir avec la couche
 * infrastructure sans en dépendre directement. Ce port permet une architecture découplée,
 * facilitant les tests, l’évolution et le changement de technologies de stockage.
 * </p>
 */
public interface ProductRepository {

    /**
     * Sauvegarde un produit dans le référentiel.
     *
     * @param product Le produit à enregistrer.
     */
    void save(Product product);

    /**
     * Vérifie si un produit existe à partir de son identifiant.
     *
     * @param productId L'identifiant unique du produit.
     * @return true si le produit existe, false sinon.
     */
    boolean existsById(String productId);

    /**
     * Recherche un produit par son identifiant.
     *
     * @param productId L'identifiant du produit recherché.
     * @return Le produit correspondant, ou null s'il n'existe pas.
     */
    Product findById(String productId);

    /**
     * Récupère la liste complète des produits disponibles.
     *
     * @return Une liste de tous les produits.
     */
    List<Product> findAll();

    /**
     * Recherche les produits correspondant à un mot-clé.
     * <p>
     * La recherche peut s'appliquer sur le nom, l'identifiant ou d'autres
     * critères selon l’implémentation.
     * </p>
     *
     * @param keyword Le mot-clé à utiliser pour la recherche.
     * @return Une liste de produits correspondant au critère.
     */
    List<Product> search(String keyword);

    /**
     * Supprime un produit à partir de son identifiant.
     *
     * @param productId L'identifiant du produit à supprimer.
     */
    void deleteById(String productId);
}
