
public class AnnCredCalc {

    private int period;
    private double rate;
    private int amount;

    public AnnCredCalc(int period, double rate, int amount) {
        this.period = period;
        this.rate = rate;
        this.amount = amount;
    }

    public double calcRate() {
        return 0;
    }

    public double calcMonthPayment() {
        calcRate();
        return 0;
    }

    public double calcAggregatePaymment() {
        return 0;
    }

    public double calcOverPayment() {
        return 0;
    }

    public double calcEffectiveRate() {
        return 0;
    }

    public double calcPercentInPaymentStructure(int period) {
        return 0;
    }

    public double calcDebtInPaymentStructure(int period) {
        return 0;
    }

    public double calcDebtRest(int period) {
        return 0;
    }
}
