import presentation.ConsoleInterface;

/**
 * Point d'entrée de l'application de gestion de stock.
 * Lance l'interface en ligne de commande permettant les opérations
 * de consultation et de modification du catalogue produit.
 *
 * Cette classe initialise le contrôleur principal.
 * Elle pourra également servir de point d’ancrage à d’autres interfaces
 * futures (web, graphique, API, etc.).
 *
 * @author Lucas
 * @version 1.0
 */
public class Main {

    /**
     * Méthode principale du programme.
     * Initialise l'interface console et démarre la boucle interactive.
     *
     * @param args arguments passés en ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        ConsoleInterface console = new ConsoleInterface();
        console.start();
    }
}