package infrastructure;

import application.ports.ProductRepository;
import domain.product.Product;

import java.util.*;

/**
 * Implémentation en mémoire de l'interface {@link ProductRepository}.
 * Cette classe fournit une solution de persistance temporaire, idéale
 * pour les phases de test, de prototypage ou d'exécution sans base de données.
 *
 * Les données sont stockées dans une Map et sont perdues à l’arrêt de l’application.
 *
 * @author Lucas
 * @version 1.1
 */
public class InMemoryProductRepository implements ProductRepository {

    private final Map<String, Product> productMap = new HashMap<>();

    /**
     * Enregistre ou met à jour un produit dans le stockage.
     *
     * @param product produit à sauvegarder
     */
    @Override
    public void save(Product product) {
        productMap.put(product.getProductId(), product);
    }

    /**
     * Vérifie si un produit existe à partir de son identifiant.
     *
     * @param productId identifiant du produit
     * @return true si le produit existe, false sinon
     */
    @Override
    public boolean existsById(String productId) {
        return productMap.containsKey(productId);
    }

    /**
     * Recherche un produit par son identifiant.
     *
     * @param productId identifiant du produit
     * @return le produit correspondant, ou null si absent
     */
    @Override
    public Product findById(String productId) {
        return productMap.get(productId);
    }

    /**
     * Retourne l’ensemble des produits enregistrés.
     *
     * @return liste de tous les produits
     */
    @Override
    public List<Product> findAll() {
        return new ArrayList<>(productMap.values());
    }

    /**
     * Recherche les produits contenant le mot-clé dans leur nom.
     * La recherche est insensible à la casse.
     *
     * @param keyword mot-clé à rechercher
     * @return liste des produits correspondants
     */
    @Override
    public List<Product> search(String keyword) {
        List<Product> results = new ArrayList<>();
        for (Product product : productMap.values()) {
            if (product.getName().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(product);
            }
        }
        return results;
    }

    /**
     * Supprime un produit du stockage à partir de son identifiant.
     *
     * @param productId identifiant du produit à supprimer
     */
    @Override
    public void deleteById(String productId) {
        productMap.remove(productId);
    }
}