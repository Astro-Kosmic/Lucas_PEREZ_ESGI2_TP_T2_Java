package domain.catalog;

import java.util.Objects;

/**
 * Représente une gamme de produits.
 * Chaque gamme est liée à une marque unique et possède un identifiant et un nom.
 * Les objets ProductLine sont considérés comme égaux s'ils partagent le même identifiant.
 */
public class ProductLine {

    private final String lineId;
    private final String name;
    private final Brand brand;

    /**
     * Construit une nouvelle gamme de produits associée à une marque.
     *
     * @param lineId identifiant unique de la gamme
     * @param name nom de la gamme
     * @param brand marque associée à cette gamme
     */
    public ProductLine(String lineId, String name, Brand brand) {
        this.lineId = lineId;
        this.name = name;
        this.brand = brand;
    }

    /**
     * Retourne l'identifiant de la gamme.
     *
     * @return identifiant de la gamme
     */
    public String getLineId() {
        return lineId;
    }

    /**
     * Retourne le nom de la gamme.
     *
     * @return nom de la gamme
     */
    public String getName() {
        return name;
    }

    /**
     * Retourne la marque associée à la gamme.
     *
     * @return objet Brand lié à cette gamme
     */
    public Brand getBrand() {
        return brand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductLine)) return false;
        ProductLine that = (ProductLine) o;
        return lineId.equals(that.lineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineId);
    }

    @Override
    public String toString() {
        return "Gamme: " + name + " (ID: " + lineId + "), Marque: " + brand.getName();
    }
}