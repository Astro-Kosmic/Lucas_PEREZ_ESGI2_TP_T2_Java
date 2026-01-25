package application;

import application.ports.ProductRepository;
import domain.product.Product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Cas d'usage : Trier les produits du stock par ordre croissant de prix.
 * Ce cas d'utilisation propose deux approches :
 * <ul>
 *     <li>Tri rapide via l'API Java standard (Comparator + sort)</li>
 *     <li>Tri manuel via l'algorithme de tri par sélection (à des fins pédagogiques ou de benchmarking)</li>
 * </ul>
 * Cette classe n'effectue aucun tri en place dans le dépôt : elle retourne une nouvelle liste triée.
 *
 * @author Lucas
 * @version 1.1
 */
public class SortProductsUseCase {

    private final ProductRepository repository;

    /**
     * Initialise le cas d'utilisation avec le référentiel des produits.
     *
     * @param repository Interface d'accès aux produits
     */
    public SortProductsUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    /**
     * Trie les produits en mémoire par prix croissant.
     * Utilise {@link java.util.Collections#sort(List, java.util.Comparator)} pour des performances optimales.
     *
     * @return liste de produits triée par prix
     */
    public List<Product> sortByPrice() {
        List<Product> products = new ArrayList<>(repository.findAll());
        products.sort(Comparator.comparingDouble(Product::getPrice));
        return products;
    }

    /**
     * Variante manuelle du tri utilisant l'algorithme de tri par sélection.
     * Moins efficace que {@link #sortByPrice()}, mais utile pour l'analyse de performance ou démonstration.
     *
     * @return liste triée par prix en utilisant un algorithme bas-niveau
     */
    public List<Product> sortByPriceSelectionSort() {
        List<Product> produits = new ArrayList<>(repository.findAll());

        for (int i = 0; i < produits.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < produits.size(); j++) {
                if (produits.get(j).getPrice() < produits.get(minIndex).getPrice()) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                Product temp = produits.get(i);
                produits.set(i, produits.get(minIndex));
                produits.set(minIndex, temp);
            }
        }
        return produits;
    }
}