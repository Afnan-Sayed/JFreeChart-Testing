import io.qameta.allure.junit4.AllureJunit4;
import org.junit.runner.JUnitCore;
import test.DiscountCalculatorTest;
import test.DiscountManagerTest;
import test.YearTest;

public class TestRunner {
    public static void main(String[] args) {
        JUnitCore junit = new JUnitCore();
        junit.addListener(new AllureJunit4());
        junit.run(YearTest.class);
        junit.run(DiscountCalculatorTest.class);
        junit.run(DiscountManagerTest.class);
    }
}