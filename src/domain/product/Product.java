package domain.product;

import domain.catalog.Brand;
import domain.catalog.ProductLine;

/**
 * Représente un produit abstrait appartenant à un catalogue.
 * Cette classe sert de base pour les types de produits concrets (ex: SimpleProduct).
 * Elle encapsule les propriétés communes à tous les produits : identifiant, nom, prix, stock,
 * ainsi que les informations de marque et de gamme.
 *
 * Cette classe est abstraite et ne peut être instanciée directement.
 *
 * @author Lucas
 * @version 1.1
 */
public abstract class Product {

    private final String id;
    private final String name;
    private double price;
    private int stock;
    private final Brand brand;
    private final ProductLine productLine;

    /**
     * Construit un produit générique avec validation des contraintes métier.
     *
     * @param id identifiant unique du produit (non nul)
     * @param name nom du produit (non nul)
     * @param price prix unitaire du produit (>= 0)
     * @param stock quantité disponible en stock (>= 0)
     * @param brand marque à laquelle appartient le produit (non nulle)
     * @param productLine gamme à laquelle appartient le produit (non nulle)
     * @throws IllegalArgumentException si un paramètre est invalide
     */
    public Product(String id, String name, double price, int stock, Brand brand, ProductLine productLine) {
        if (id == null) throw new IllegalArgumentException("id cannot be null");
        if (name == null) throw new IllegalArgumentException("name cannot be null");
        if (price < 0) throw new IllegalArgumentException("price must be equal or greater than zero");
        if (stock < 0) throw new IllegalArgumentException("stock must be equal or greater than zero");
        if (brand == null) throw new IllegalArgumentException("brand cannot be null");
        if (productLine == null) throw new IllegalArgumentException("product line cannot be null");

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.brand = brand;
        this.productLine = productLine;
    }

    /**
     * Retourne l'identifiant unique du produit.
     *
     * @return identifiant du produit
     */
    public String getProductId() {
        return id;
    }

    /**
     * Retourne le nom du produit.
     *
     * @return nom du produit
     */
    public String getName() {
        return name;
    }

    /**
     * Retourne le prix unitaire du produit.
     *
     * @return prix du produit
     */
    public double getPrice() {
        return price;
    }

    /**
     * Retourne la quantité en stock.
     *
     * @return stock actuel
     */
    public int getStock() {
        return stock;
    }

    /**
     * Retourne la marque associée au produit.
     *
     * @return marque
     */
    public Brand getBrand() {
        return brand;
    }

    /**
     * Retourne la gamme à laquelle appartient le produit.
     *
     * @return gamme
     */
    public ProductLine getProductLine() {
        return productLine;
    }

    @Override
    public String toString() {
        return name + " | ID: " + id + " | Prix: " + price + " | Stock: " + stock +
                " | Marque: " + brand.getName() + " | Gamme: " + productLine.getName();
    }
}