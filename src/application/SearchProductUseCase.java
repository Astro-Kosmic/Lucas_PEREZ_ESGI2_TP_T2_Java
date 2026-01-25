package application;

import application.ports.ProductRepository;
import domain.product.Product;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Cas d'usage : Rechercher des produits dans le stock.
 * Permet une recherche ciblée selon l'identifiant, le nom ou les deux.
 * Recherche insensible à la casse et robuste aux chaînes vides.
 *
 * @author Lucas
 * @version 1.2
 */
public class SearchProductUseCase {

    /**
     * Modes de recherche disponibles.
     */
    public enum Mode {
        ID, NAME, BOTH
    }

    private final ProductRepository productRepository;

    public SearchProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Recherche les produits selon le mode spécifié.
     * Si aucun mot-clé valide n'est fourni, retourne l'intégralité des produits.
     *
     * @param keyword Mot-clé utilisé pour filtrer les produits
     * @param mode Mode de recherche (ID, NAME ou BOTH)
     * @return Liste filtrée des produits correspondant au critère
     */
    public List<Product> execute(String keyword, Mode mode) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return productRepository.findAll();
        }

        final String lowered = keyword.toLowerCase();
        return productRepository.findAll().stream()
                .filter(p ->
                        (mode == Mode.ID || mode == Mode.BOTH) && p.getProductId().toLowerCase().contains(lowered)
                                || (mode == Mode.NAME || mode == Mode.BOTH) && p.getName().toLowerCase().contains(lowered))
                .collect(Collectors.toList());
    }
}