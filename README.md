# Stock Management System

##  Auteur
**Lucas PEREZ**  
Projet POO en JAVA — ESGI2 Campus Éductive  
Trimestre 2 – 2025/2026

---

Ce projet est une application Java orientée objet permettant de gérer un stock de produits via une interface en ligne de commande. Le système respecte une architecture propre inspirée du modèle hexagonal (ports et adaptateurs).

## Choix de conception

Plusieurs décisions architecturales ont été prises afin de garantir
la maintenabilité et l’évolutivité du projet :

- Les règles métier (ex : remise commerciale) sont isolées dans des
  objets dédiés (`DiscountPolicy`) afin de respecter le principe
  Open/Closed (SOLID).

- Les cas d’usage applicatifs ne contiennent pas de logique
  d’affichage, celle-ci étant limitée à la couche `presentation`.

- Le stockage repose sur une abstraction (`ProductRepository`)
  permettant de remplacer facilement l’implémentation mémoire
  par une base de données réelle sans modifier le domaine métier.

## Fonctionnalités principales

### Gestion du catalogue
- Ajout de produits avec génération automatique d’identifiant
- Association des produits à une marque et une gamme
- Suppression de produits existants
- Recherche par identifiant, nom ou combinaison des deux
- Affichage et tri des produits par prix

### Vente et logique métier (TP2)
- Vente de produits via un panier contenant jusqu’à trois articles
- Vérification automatique de la disponibilité du stock
- Calcul du total du panier
- Application d’une règle commerciale de remise (5% si le total dépasse 20€)
- Mise à jour automatique du stock après validation
- Génération d’un ticket de caisse détaillé

## Architecture

Le projet suit une organisation claire par packages :
- `domain` : cœur métier (catalogue + produits + vente)  
  - `domain.catalog` : métadonnées catalogue (`Brand`, `ProductLine`, `Catalog`)  
  - `domain.product` : entités produits (`Product`, `SimpleProduct`, `ContainerProduct`)  
  - `domain.sale` : logique de vente (`CartLine`, `Receipt`, `ReceiptLine`, `DiscountPolicy`)  
- `application` : cas d’usages (orchestration métier : `AddProductUseCase`, `SellProductUseCase`, `DeleteProductUseCase`, etc.)
- `application.ports` : interfaces (ports) d’accès aux données (`ProductRepository`)
- `infrastructure` : implémentations concrètes (stockage en mémoire, `ProductIdGenerator`, `InMemoryProductRepository`)
- `presentation` : interface en ligne de commande (`ConsoleInterface`)
- `test` : tests manuels (avec `TestRunner`) et unitaires (JUnit)

## Évolutions possibles

- Remplacement du stockage mémoire par une base de données.
- Ajout d’une interface graphique sans modification du domaine.
- Extension du système de remise via plusieurs stratégies commerciales.

## Technologies utilisées

- Java 17+
- Doxygen pour la documentation
- Pas de dépendance externe, exécutable depuis le terminal

## Lancement de l'application

```bash
javac src/Main.java
java -cp src Main
```

## Lancer les tests manuels

```bash
javac src/test/TestRunner.java
java -cp src test.TestRunner
```

## Génération de documentation Doxygen

Un fichier `Doxyfile` est fourni. Pour générer la documentation :

```bash
doxygen Doxyfile
```

Le fichier `docs/index.html` peut être ouvert dans un navigateur pour consulter la documentation.