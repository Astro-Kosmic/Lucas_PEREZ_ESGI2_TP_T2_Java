package presentation;

import application.*;
import application.ports.ProductRepository;
import domain.catalog.Brand;
import domain.catalog.Catalog;
import domain.catalog.ProductLine;
import domain.product.*;
import infrastructure.InMemoryProductRepository;
import infrastructure.ProductIdGenerator;

import java.util.List;
import java.util.Scanner;

/**
 * Interface utilisateur en ligne de commande pour la gestion de stock.
 * Permet l'ajout, la suppression, la recherche, la vente et l'affichage de produits.
 * Sert de point d'entrée principal de la couche de présentation.
 *
 * Cette classe orchestre les cas d'utilisation métier à partir des saisies utilisateur.
 *
 * @author Lucas
 * @version 1.1
 */
public class ConsoleInterface {

    private final Scanner scanner = new Scanner(System.in);
    private final ProductRepository repository = new InMemoryProductRepository();
    private final Catalog catalog = new Catalog();

    private final AddProductUseCase addProduct = new AddProductUseCase(repository);
    private final SellProductUseCase sellProduct = new SellProductUseCase(repository);
    private final DeleteProductUseCase deleteProduct = new DeleteProductUseCase(repository);
    private final SearchProductUseCase searchProduct = new SearchProductUseCase(repository);
    private final SortProductsUseCase sortProducts = new SortProductsUseCase(repository);

    /**
     * Lance le menu principal en boucle jusqu'à la demande de sortie.
     */
    public void start() {
        System.out.println("Bienvenue dans le système de gestion de stock.");

        while (true) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Ajouter un produit");
            System.out.println("2. Vendre un produit");
            System.out.println("3. Supprimer un produit");
            System.out.println("4. Rechercher un produit");
            System.out.println("5. Afficher tous les produits");
            System.out.println("0. Quitter");
            System.out.print("Choix : ");

            String input = scanner.nextLine();
            switch (input) {
                case "1" -> ajouterProduit();
                case "2" -> vendreProduit();
                case "3" -> supprimerProduit();
                case "4" -> rechercherProduit();
                case "5" -> afficherProduits();
                case "0" -> { System.out.println("Au revoir !"); return; }
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    /**
     * Ajoute un nouveau produit en demandant les informations nécessaires à l'utilisateur.
     * L'ID est généré automatiquement de manière incrémentale.
     */
    private void ajouterProduit() {
        String id = ProductIdGenerator.generateId();
        System.out.println("ID généré automatiquement : " + id);

        System.out.print("Nom : ");
        String name = scanner.nextLine();
        System.out.print("Prix : ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Stock : ");
        int stock = Integer.parseInt(scanner.nextLine());

        System.out.print("ID Marque : ");
        String brandId = scanner.nextLine();
        System.out.print("Nom Marque : ");
        String brandName = scanner.nextLine();

        System.out.print("ID Gamme : ");
        String lineId = scanner.nextLine();
        System.out.print("Nom Gamme : ");
        String lineName = scanner.nextLine();

        Brand brand = catalog.createBrand(brandId, brandName);
        ProductLine line = catalog.createProductLine(lineId, lineName, brand);

        Product p = new SimpleProduct(id, name, price, stock, brand, line);
        try {
            addProduct.execute(p);
            System.out.println("Produit ajouté avec succès.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    /**
     * Permet à l'utilisateur de vendre un produit en réduisant son stock.
     */
    private void vendreProduit() {
        afficherProduits();
        System.out.print("ID produit : ");
        String id = scanner.nextLine();
        System.out.print("Quantité à vendre : ");
        int qty = Integer.parseInt(scanner.nextLine());

        try {
            sellProduct.execute(id, qty);
            System.out.println("Vente enregistrée.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    /**
     * Supprime un produit du stock après confirmation de l'utilisateur.
     */
    private void supprimerProduit() {
        afficherProduits();
        System.out.print("ID produit à supprimer : ");
        String id = scanner.nextLine();
        System.out.print("Confirmer la suppression ? (y/n) : ");
        String confirm = scanner.nextLine();

        if (!confirm.equalsIgnoreCase("y")) {
            System.out.println("Suppression annulée.");
            return;
        }

        try {
            deleteProduct.execute(id);
            System.out.println("Produit supprimé.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    /**
     * Recherche des produits selon un critère choisi (ID, nom, ou les deux).
     */
    private void rechercherProduit() {
        System.out.println("Choisissez le mode de recherche :");
        System.out.println("1. Par ID");
        System.out.println("2. Par nom");
        System.out.println("3. Par ID ou nom");
        System.out.print("Choix : ");
        String mode = scanner.nextLine();

        SearchProductUseCase.Mode searchMode;
        switch (mode) {
            case "1" -> searchMode = SearchProductUseCase.Mode.ID;
            case "2" -> searchMode = SearchProductUseCase.Mode.NAME;
            case "3" -> searchMode = SearchProductUseCase.Mode.BOTH;
            default -> {
                System.out.println("Mode invalide.");
                return;
            }
        }

        System.out.print("Mot-clé : ");
        String keyword = scanner.nextLine();

        List<Product> results = searchProduct.execute(keyword, searchMode);
        if (results.isEmpty()) {
            System.out.println("Aucun résultat.");
        } else {
            System.out.println("Résultats :");
            for (Product p : results) {
                System.out.println(p);
            }
        }
    }

    /**
     * Affiche l'ensemble des produits du stock, triés par prix croissant.
     */
    private void afficherProduits() {
        List<Product> produits = sortProducts.sortByPrice();
        if (produits.isEmpty()) {
            System.out.println("Aucun produit en stock.");
            return;
        }

        System.out.println("\n--- Liste des produits en stock ---");
        for (Product p : produits) {
            System.out.println(p);
        }
    }
}