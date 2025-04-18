package test;
import JFree.DiscountManager;
import JFree.IDiscountCalculator;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import static org.junit.Assert.*;


public class DiscountManagerTest {

    @Test
    public void testCalculatePriceWhenDiscountsSeasonIsFalse() throws Exception {
        // Arrange
        boolean isDiscountsSeason = false;
        double originalPrice = 100.0;
        double expectedPrice = 100.0;

        //mocking obj
        Mockery mockingContext = new Mockery();

        //mocked class
        IDiscountCalculator mockedDependency = mockingContext.mock(IDiscountCalculator.class);
        mockingContext.checking(new Expectations(){
            {
                // make sure that none of the functions are called
                never(mockedDependency).isTheSpecialWeek();
                never(mockedDependency).getDiscountPercentage();
            }
        });
        DiscountManager discountManager = new DiscountManager(isDiscountsSeason, mockedDependency);
        // Act
        double actual=discountManager.calculatePriceAfterDiscount(originalPrice);

        // Assert
        // make sure that mocking Expectations Is Satisfied
        mockingContext.assertIsSatisfied();

        // make sure that the actual value exactly equals the expected value
        assertEquals(expectedPrice, actual);
    }

    @Test
    public void testCalculatePriceWhenDiscountsSeasonIsTrueAndSpecialWeekIsTrue() throws Exception {

    }

    // test missing cases
}
