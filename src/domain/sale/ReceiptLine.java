package domain.sale;

/**
 * Ligne d'un ticket de caisse.
 * Contient les informations calculées pour une ligne : nom, quantité, prix unitaire, total ligne.
 *
 * @author Lucas
 * @version 1.0
 */
public class ReceiptLine {

    private final String productName;
    private final int quantity;
    private final double unitPrice;
    private final double lineTotal;

    public ReceiptLine(String productName, int quantity, double unitPrice) {
        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("productName cannot be null or blank");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero");
        }
        if (unitPrice < 0) {
            throw new IllegalArgumentException("unitPrice must be >= 0");
        }
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.lineTotal = unitPrice * quantity;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getLineTotal() {
        return lineTotal;
    }
}
