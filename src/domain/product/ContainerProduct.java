package domain.product;

import domain.catalog.Brand;
import domain.catalog.ProductLine;

/**
 * Représente un produit conteneur, c’est-à-dire un produit composé
 * qui contient une certaine quantité d’un autre produit.
 * Ce type de produit est utile pour les lots, packs ou combinaisons de produits.
 *
 * Hérite des propriétés de base d’un {@link Product}.
 *
 * @author Lucas
 * @version 1.1
 */
public class ContainerProduct extends Product {

    private final Product containedProduct;
    private final int containedQuantity;

    /**
     * Construit un produit conteneur avec son contenu et ses attributs de base.
     *
     * @param id identifiant unique du conteneur
     * @param name nom du conteneur
     * @param price prix du conteneur (peut être différent du produit contenu)
     * @param stock quantité en stock du conteneur
     * @param containedProduct produit contenu (ne peut pas être nul)
     * @param containedQuantity quantité du produit contenu (strictement positive)
     * @param brand marque associée
     * @param productLine gamme associée
     * @throws IllegalArgumentException si le produit contenu est nul ou si la quantité est invalide
     */
    public ContainerProduct(String id, String name, double price, int stock,
                            Product containedProduct, int containedQuantity,
                            Brand brand, ProductLine productLine) {

        super(id, name, price, stock, brand, productLine);

        if (containedProduct == null) {
            throw new IllegalArgumentException("containedProduct cannot be null");
        }
        if (containedQuantity <= 0) {
            throw new IllegalArgumentException("containedQuantity must be greater than zero");
        }

        this.containedProduct = containedProduct;
        this.containedQuantity = containedQuantity;
    }

    /**
     * Retourne le produit contenu dans ce conteneur.
     *
     * @return produit encapsulé
     */
    public Product getContainedProduct() {
        return containedProduct;
    }

    /**
     * Retourne la quantité du produit contenu.
     *
     * @return quantité encapsulée
     */
    public int getContainedQuantity() {
        return containedQuantity;
    }
}