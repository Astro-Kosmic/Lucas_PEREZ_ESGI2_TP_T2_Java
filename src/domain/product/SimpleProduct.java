package domain.product;

import domain.catalog.Brand;
import domain.catalog.ProductLine;

/**
 * Représente un produit simple du catalogue.
 * Il s'agit d'un produit unitaire, non composite, dérivé de la classe abstraite {@link Product}.
 * Ce type de produit ne contient ni sous-produits ni structure hiérarchique.
 *
 * Cette classe est utilisée pour les cas standards de gestion de stock.
 *
 * @author Lucas
 * @version 1.1
 */
public class SimpleProduct extends Product {

    /**
     * Construit une instance de produit simple avec ses attributs fondamentaux.
     *
     * @param id identifiant unique du produit
     * @param name nom du produit
     * @param price prix unitaire (>= 0)
     * @param stock quantité disponible (>= 0)
     * @param brand marque associée au produit (non nulle)
     * @param productLine gamme à laquelle appartient le produit (non nulle)
     */
    public SimpleProduct(String id, String name, double price, int stock, Brand brand, ProductLine productLine) {
        super(id, name, price, stock, brand, productLine);
    }
}