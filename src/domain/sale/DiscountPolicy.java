package domain.sale;

/**
 * Politique de remise appliquée sur un total de panier.
 * Règle TP2 : 5% de remise si total > 20€.
 *
 * @author Lucas
 * @version 1.0
 */
public class DiscountPolicy {

    private final double threshold;
    private final double rate;

    /**
     * @param threshold seuil à dépasser pour appliquer la remise (ex: 20.0)
     * @param rate taux de remise (ex: 0.05 pour 5%)
     */
    public DiscountPolicy(double threshold, double rate) {
        if (threshold < 0) {
            throw new IllegalArgumentException("threshold must be >= 0");
        }
        if (rate < 0 || rate > 1) {
            throw new IllegalArgumentException("rate must be between 0 and 1");
        }
        this.threshold = threshold;
        this.rate = rate;
    }

    /**
     * Calcule le montant de la remise à appliquer en fonction du total brut.
     *
     * @param grossTotal total brut du panier
     * @return montant de la remise
     */
    public double computeDiscount(double grossTotal) {
        if (grossTotal > threshold) {
            return grossTotal * rate;
        }
        return 0.0;
    }

    public double getThreshold() {
        return threshold;
    }

    public double getRate() {
        return rate;
    }
}