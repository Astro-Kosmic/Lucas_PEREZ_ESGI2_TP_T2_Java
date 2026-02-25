package domain.sale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Ticket de caisse (résultat d'une vente).
 * Contient les lignes, le total brut, la remise éventuelle et le total net.
 *
 * @author Lucas
 * @version 1.0
 */
public class Receipt {

    private final List<ReceiptLine> lines = new ArrayList<>();
    private final double grossTotal;
    private final double discountAmount;
    private final double netTotal;

    public Receipt(List<ReceiptLine> lines, double grossTotal, double discountAmount, double netTotal) {
        if (lines == null) {
            throw new IllegalArgumentException("lines cannot be null");
        }
        this.lines.addAll(lines);
        this.grossTotal = grossTotal;
        this.discountAmount = discountAmount;
        this.netTotal = netTotal;
    }

    public List<ReceiptLine> getLines() {
        return Collections.unmodifiableList(lines);
    }

    public double getGrossTotal() {
        return grossTotal;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public double getNetTotal() {
        return netTotal;
    }

    /**
     * Retourne une version texte prête à afficher dans la console.
     */
    public String formatForConsole() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n====== TICKET DE CAISSE ======\n");
        for (ReceiptLine line : lines) {
            sb.append("- ")
                    .append(line.getProductName())
                    .append(" | Qté: ").append(line.getQuantity())
                    .append(" | PU: ").append(String.format("%.2f€", line.getUnitPrice()))
                    .append(" | Total: ").append(String.format("%.2f€", line.getLineTotal()))
                    .append("\n");
        }
        sb.append("-----------------------------\n");
        sb.append("Total brut : ").append(String.format("%.2f€", grossTotal)).append("\n");
        sb.append("Remise     : ").append(String.format("%.2f€", discountAmount)).append("\n");
        sb.append("Total net  : ").append(String.format("%.2f€", netTotal)).append("\n");
        sb.append("=============================\n");
        return sb.toString();
    }
}