import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AnnCalcTest {

    final static double TESTRATE = 20; //процентов годовых
    final static int TESTPERIOD = 12; //срок месяцев
    final static int TESTAMOUNT = 10000; //сумма в единицах валюты

    AnnCredCalc calc = new AnnCredCalc(TESTPERIOD, TESTRATE, TESTAMOUNT);

    @Test
    public void calcRate() {
        double methodResult = calc.calcRate();
        double testResult = TESTRATE / 100 / 12;
        Assert.assertTrue("Wrong result for rate calculation", methodResult == testResult);
    }

    @Test
    public void countMonthPayment() {
        double tmpRate = calc.calcRate();
        double methodResult = calc.calcMonthPayment();
        double testResult = TESTAMOUNT * (tmpRate * Math.pow((1 + tmpRate), TESTPERIOD)) / (Math.pow((1 + tmpRate), TESTPERIOD) - 1);
        Assert.assertTrue("Wrong result for month payment calculation", methodResult == testResult);
    }

    @Test
    public void calcAggregatePaymentTest() {
        double methodResult = calc.calcAggregatePaymment();
        double testResult = calc.calcMonthPayment() * TESTPERIOD;
        Assert.assertTrue("Wrong result for aggregate payment calculation", (methodResult == testResult) && (methodResult != 0));
    }

    @Test
    public void calcOverPaymentTest() {
        double methodResult = calc.calcOverPayment();
        double testResult = calc.calcAggregatePaymment() - TESTAMOUNT;
        Assert.assertTrue("Wrong result for overpayment calculation", methodResult == testResult);
    }

    @Test
    public void calcEffectiveRateTest() {
        double methodResult = calc.calcEffectiveRate();
        double testResult = calc.calcOverPayment() / TESTAMOUNT * 100;
        Assert.assertTrue("Wrong result for effective rate calculation", (methodResult == testResult) && (methodResult != 0));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, TESTPERIOD})
    public void calcPaymentStructure(int arg) {

        double monthPayment = calc.calcMonthPayment();
        double debtRest = TESTAMOUNT;
        double percents = 0;
        double debtDecrement = 0;
        for (int i = 0; i < arg; i++) {
            percents = debtRest * calc.calcRate();
            debtDecrement = monthPayment - percents;
            debtRest -= debtDecrement;
        }
        Assertions.assertTrue( (debtRest == calc.calcDebtRest(arg)) &&
                (percents == calc.calcPercentInPaymentStructure(arg)) && (debtDecrement == calc.calcDebtInPaymentStructure(arg)));
    }

}
