package domain.catalog;

import java.util.Objects;

/**
 * Représente une marque à laquelle peuvent être associées une ou plusieurs gammes de produits.
 * Chaque marque est identifiée de manière unique par un identifiant stable (brandId)
 * et possède un nom affiché lisible par un humain.
 *
 * @author Lucas
 * @version 1.1
 */
public class Brand {

    private final String brandId;
    private final String name;

    /**
     * Construit une instance de Brand avec un identifiant et un nom.
     *
     * @param brandId identifiant unique de la marque (non nul)
     * @param name nom lisible de la marque (non nul)
     * @throws IllegalArgumentException si l'un des paramètres est nul
     */
    public Brand(String brandId, String name) {
        if (brandId == null || name == null) {
            throw new IllegalArgumentException("L'identifiant et le nom de la marque ne peuvent pas être nuls.");
        }
        this.brandId = brandId;
        this.name = name;
    }

    /**
     * Retourne l'identifiant unique de la marque.
     *
     * @return identifiant de la marque
     */
    public String getBrandId() {
        return brandId;
    }

    /**
     * Retourne le nom de la marque.
     *
     * @return nom de la marque
     */
    public String getName() {
        return name;
    }

    /**
     * Vérifie l'égalité entre deux marques sur la base de leur identifiant.
     *
     * @param o objet à comparer
     * @return true si les identifiants sont identiques, false sinon
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Brand)) return false;
        Brand brand = (Brand) o;
        return brandId.equals(brand.brandId);
    }

    /**
     * Calcule le hashcode basé sur l'identifiant de la marque.
     *
     * @return valeur de hachage
     */
    @Override
    public int hashCode() {
        return Objects.hash(brandId);
    }

    /**
     * Fournit une représentation textuelle lisible de la marque.
     *
     * @return chaîne descriptive de la marque
     */
    @Override
    public String toString() {
        return "Marque: " + name + " (ID: " + brandId + ")";
    }
}