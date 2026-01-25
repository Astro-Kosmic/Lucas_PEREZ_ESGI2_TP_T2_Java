package infrastructure;

/**
 * Générateur d'identifiants uniques pour les produits.
 * Garantit l'unicité des IDs via un incrément interne.
 *
 * Utilisé pour éviter la duplication d'identifiants produits
 * lors de l'ajout de nouveaux éléments au stock.
 *
 * Ce générateur est thread-safe via synchronisation explicite.
 *
 * @author Lucas
 * @version 1.1
 */
public class ProductIdGenerator {

    // Dernier ID généré (persisté uniquement en mémoire)
    private static int lastId = 0;

    /**
     * Génère un nouvel identifiant unique sous forme de chaîne.
     *
     * @return identifiant produit incrémental (ex: "1", "2", "3", ...)
     */
    public static synchronized String generateId() {
        lastId++;
        return String.valueOf(lastId);
    }

    /**
     * Initialise manuellement l'état interne du générateur.
     * Permet d'éviter les collisions si des produits préexistants
     * sont rechargés depuis une base externe ou un fichier.
     *
     * @param lastUsedId identifiant le plus élevé actuellement utilisé
     */
    public static synchronized void initializeFrom(int lastUsedId) {
        if (lastUsedId > lastId) {
            lastId = lastUsedId;
        }
    }
}