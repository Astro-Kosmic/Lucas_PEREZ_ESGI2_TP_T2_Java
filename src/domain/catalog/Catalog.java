package domain.catalog;

import java.util.*;

/**
 * Fournit un registre centralisé pour la gestion des marques ({@link Brand})
 * et des gammes de produits ({@link ProductLine}).
 * <p>
 * Ce composant encapsule la logique de création, d'accès et de regroupement
 * des entités métier liées au catalogue produit.
 * </p>
 *
 * @author Lucas
 * @version 1.2
 */
public class Catalog {

    private final Map<String, Brand> brands = new HashMap<>();
    private final Map<String, ProductLine> productLines = new HashMap<>();

    /**
     * Crée une marque si elle n'existe pas encore dans le registre.
     * Deux marques avec le même identifiant sont considérées comme identiques.
     * Cette méthode est idempotente.
     *
     * @param id identifiant unique de la marque
     * @param name nom lisible de la marque
     * @return la marque existante ou nouvellement créée
     * @throws IllegalArgumentException si l'identifiant est nul
     */
    public Brand createBrand(String id, String name) {
        if (id == null) throw new IllegalArgumentException("Brand ID ne peut pas être null.");
        return brands.computeIfAbsent(id, key -> new Brand(id, name));
    }

    /**
     * Crée une gamme de produits rattachée à une marque si elle n'existe pas déjà.
     * Deux gammes avec le même identifiant sont considérées comme identiques.
     * Cette méthode est idempotente.
     *
     * @param lineId identifiant unique de la gamme
     * @param name nom de la gamme
     * @param brand marque associée
     * @return la gamme existante ou nouvellement créée
     * @throws IllegalArgumentException si l'identifiant ou la marque sont nuls
     */
    public ProductLine createProductLine(String lineId, String name, Brand brand) {
        if (lineId == null) throw new IllegalArgumentException("ProductLine ID ne peut pas être null.");
        if (brand == null) throw new IllegalArgumentException("Brand associée ne peut pas être null.");
        return productLines.computeIfAbsent(lineId, key -> new ProductLine(lineId, name, brand));
    }

    /**
     * Retourne toutes les marques actuellement enregistrées dans le catalogue.
     *
     * @return liste des marques
     */
    public List<Brand> getAllBrands() {
        return new ArrayList<>(brands.values());
    }

    /**
     * Retourne toutes les gammes de produits enregistrées.
     *
     * @return liste des gammes
     */
    public List<ProductLine> getAllProductLines() {
        return new ArrayList<>(productLines.values());
    }

    /**
     * Recherche une marque à partir de son identifiant.
     *
     * @param id identifiant de la marque
     * @return un Optional contenant la marque si elle est trouvée
     */
    public Optional<Brand> findBrandById(String id) {
        return Optional.ofNullable(brands.get(id));
    }

    /**
     * Recherche une gamme à partir de son identifiant.
     *
     * @param id identifiant de la gamme
     * @return un Optional contenant la gamme si elle est trouvée
     */
    public Optional<ProductLine> findProductLineById(String id) {
        return Optional.ofNullable(productLines.get(id));
    }

    /**
     * Retourne toutes les gammes associées à une marque donnée.
     *
     * @param brand instance de la marque cible
     * @return liste des gammes rattachées à cette marque
     */
    public List<ProductLine> findLinesByBrand(Brand brand) {
        List<ProductLine> results = new ArrayList<>();
        for (ProductLine line : productLines.values()) {
            if (line.getBrand().equals(brand)) {
                results.add(line);
            }
        }
        return results;
    }
}