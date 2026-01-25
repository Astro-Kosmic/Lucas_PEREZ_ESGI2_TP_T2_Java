# Stock Management System

##  Auteur
**Lucas PEREZ**  
Projet POO en JAVA — ESGI2 Campus Éductive  
Trimestre 2 – 2025/2026

---

Ce projet est une application Java orientée objet permettant de gérer un stock de produits via une interface en ligne de commande. Le système respecte une architecture propre inspirée du modèle hexagonal (ports et adaptateurs).

## Fonctionnalités principales

- **Ajout de produit** : avec génération automatique d'ID unique.
- **Suppression de produit** : confirmation requise, traitement sécurisé.
- **Vente de produit** : met à jour le stock en temps réel.
- **Recherche de produit** : par ID, nom ou les deux, insensible à la casse.
- **Tri de produits** : par prix croissant (Java sort ou sélection manuelle).
- **Gestion des marques et gammes** : enregistrement unique, centralisé.

## Architecture

Le projet suit une organisation claire par packages :
- `domain` : entités métiers (`Product`, `Brand`, `ProductLine`, etc.)
- `application` : cas d'usages (`AddProductUseCase`, etc.)
- `infrastructure` : implémentations concrètes (stockage en mémoire, ID generator)
- `presentation` : interface en ligne de commande (`ConsoleInterface`)
- `test` : tests manuels (avec `TestRunner`) et unitaires (JUnit)

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