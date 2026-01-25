package test;

import application.*;
import infrastructure.InMemoryProductRepository;
import domain.catalog.Brand;
import domain.catalog.ProductLine;
import domain.product.Product;
import domain.product.SimpleProduct;
import infrastructure.ProductIdGenerator;

import java.util.List;

/**
 * Classe de test manuelle de l'application de gestion de stock.
 * Contient un ensemble de tests unitaires simples exécutés depuis la ligne de commande,
 * sans utilisation de framework externe.
 *
 * Cette classe couvre :
 * - La création de produits
 * - L'ajout, la recherche et la suppression de produits
 * - La génération automatique d'identifiants
 * - La recherche par nom
 *
 * @author Lucas
 * @version 1.0
 */
public class TestRunner {

    /**
     * Point d'entrée principal pour exécuter les tests unitaires manuels.
     * Chaque méthode de test est appelée et une confirmation s'affiche si elle passe.
     */
    public static void main(String[] args) {
        System.out.println("=== Lancement des tests manuels ===");

        testProductCreation();
        testAddAndFindProduct();
        testProductIdGeneration();
        testDeleteProduct();
        testSearchProduct();

        System.out.println("=== Tous les tests sont passés ===");
    }

    /**
     * Vérifie la bonne création d'un produit avec ses attributs.
     */
    static void testProductCreation() {
        Brand brand = new Brand("b1", "Nike");
        ProductLine line = new ProductLine("l1", "Chaussures", brand);
        Product p = new SimpleProduct("1", "Air Max", 120.0, 10, brand, line);

        assert "Air Max".equals(p.getName());
        assert p.getPrice() == 120.0;
        assert p.getStock() == 10;

        System.out.println("[OK] testProductCreation");
    }

    /**
     * Teste l'ajout d'un produit au repository ainsi que sa récupération.
     */
    static void testAddAndFindProduct() {
        var repo = new InMemoryProductRepository();
        Brand brand = new Brand("b2", "Adidas");
        ProductLine line = new ProductLine("l2", "Textile", brand);
        Product p = new SimpleProduct("2", "T-Shirt", 30.0, 15, brand, line);

        repo.save(p);
        assert repo.existsById("2");
        assert "T-Shirt".equals(repo.findById("2").getName());

        System.out.println("[OK] testAddAndFindProduct");
    }

    /**
     * Vérifie que le générateur d'identifiants produit bien des ID uniques et croissants.
     */
    static void testProductIdGeneration() {
        String id1 = ProductIdGenerator.generateId();
        String id2 = ProductIdGenerator.generateId();
        assert !id1.equals(id2);
        assert Integer.parseInt(id2) > Integer.parseInt(id1);

        System.out.println("[OK] testProductIdGeneration");
    }

    /**
     * Vérifie que la suppression d'un produit fonctionne correctement.
     */
    static void testDeleteProduct() {
        var repo = new InMemoryProductRepository();
        Brand brand = new Brand("b3", "Puma");
        ProductLine line = new ProductLine("l3", "Running", brand);
        Product p = new SimpleProduct("3", "Chaussure", 90.0, 5, brand, line);

        repo.save(p);
        assert repo.existsById("3");
        repo.deleteById("3");
        assert !repo.existsById("3");

        System.out.println("[OK] testDeleteProduct");
    }

    /**
     * Teste la recherche de produit par nom en utilisant la couche application.
     */
    static void testSearchProduct() {
        var repo = new InMemoryProductRepository();
        repo.save(new SimpleProduct("1", "Basket Rouge", 50, 10, new Brand("b", "Nike"), new ProductLine("l", "Sport", new Brand("b", "Nike"))));
        repo.save(new SimpleProduct("2", "Veste Noire", 80, 5, new Brand("b", "Nike"), new ProductLine("l", "Sport", new Brand("b", "Nike"))));

        SearchProductUseCase search = new SearchProductUseCase(repo);
        List<Product> results = search.execute("basket", SearchProductUseCase.Mode.NAME);
        assert results.size() == 1;
        assert results.get(0).getName().equals("Basket Rouge");

        System.out.println("[OK] testSearchProduct");
    }
}