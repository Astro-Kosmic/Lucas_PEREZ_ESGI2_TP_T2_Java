package domain.sale;

/**
 * Représente une ligne de panier (produit + quantité).
 *
 * @author Lucas
 * @version 1.0
 */
public class CartLine {

    private final String productId;
    private final int quantity;

    /**
     * @param productId identifiant du produit (non nul / non vide)
     * @param quantity quantité demandée (strictement positive)
     */
    public CartLine(String productId, int quantity) {
        if (productId == null || productId.isBlank()) {
            throw new IllegalArgumentException("productId cannot be null or blank");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero");
        }
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}