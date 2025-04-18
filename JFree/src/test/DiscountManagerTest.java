package test;
import JFree.DiscountCalculator;
import JFree.DiscountManager;
import JFree.IDiscountCalculator;
import org.jfree.data.time.Week;
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
        assertEquals(expectedPrice, actual, 0.01);
    }

    @Test
    public void testCalculatePriceWhenDiscountsSeasonIsTrueAndSpecialWeekIsTrue() throws Exception
    {
        //Arrange
        boolean isDiscountsSeason = true;
        double originalPrice = 100.0;
        double expectedPrice = 80.0;

        //mocking obj
        Mockery mockingContext = new Mockery();

        //mocked class
        IDiscountCalculator mockedDependency = mockingContext.mock(IDiscountCalculator.class);

        //expectation
        mockingContext.checking(new Expectations(){
            {
                //i used this and not 'allowing' as allowing has the probability of not calling this function,
                //but in this test case it is a must to be called and only once
                oneOf(mockedDependency).isTheSpecialWeek();
                will(returnValue(true));

                //and never call getDiscountPercentage
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
        assertEquals(expectedPrice, actual, 0.01);

    }

    @Test
    public void testCalculatePriceWhenDiscountsSeasonIsTrueAndSpecialWeekIsFalseAndWeekNumberIsEven () throws Exception
    {
        //Arrange
        boolean isDiscountsSeason = true;

        Week w = new Week(28, 2025);

        //the concrete class to test its logic when week num id even
        DiscountCalculator concreteCalc= new DiscountCalculator(w);

        double originalPrice = 100.0;
        double expectedPrice = originalPrice * concreteCalc.getDiscountPercentage(); //70.0

        //mocking obj
        Mockery mockingContext = new Mockery();

        //mocked class
        IDiscountCalculator mockedDependency = mockingContext.mock(IDiscountCalculator.class);

        //expectation
        mockingContext.checking(new Expectations(){
            {
                oneOf(mockedDependency).getDiscountPercentage();
                    will(returnValue(7));

                oneOf(mockedDependency).isTheSpecialWeek();
                    will(returnValue(false));
            }
        });

        DiscountManager discountManager = new DiscountManager(isDiscountsSeason, mockedDependency);

        // Act
        double actual=discountManager.calculatePriceAfterDiscount(originalPrice);

        // Assert
        // make sure that mocking Expectations Is Satisfied
        mockingContext.assertIsSatisfied();

        // make sure that the actual value exactly equals the expected value
        assertEquals(expectedPrice, actual, 0.01);

    }

    @Test
    public void testCalculatePriceWhenDiscountsSeasonIsTrueAndSpecialWeekIsFalseAndWeekNumberIsOdd () throws Exception
    {
        //Arrange
        boolean isDiscountsSeason = true;

        Week w = new Week(27, 2025);

        //the concrete class to test its logic when week num id even
        DiscountCalculator concreteCalc= new DiscountCalculator(w);

        double originalPrice = 100.0;
        double expectedPrice = originalPrice* concreteCalc.getDiscountPercentage(); //50.0

        //mocking obj
        Mockery mockingContext = new Mockery();

        //mocked class
        IDiscountCalculator mockedDependency = mockingContext.mock(IDiscountCalculator.class);

        //expectation
        mockingContext.checking(new Expectations(){
            {
                oneOf(mockedDependency).getDiscountPercentage();
                    will(returnValue(5));

                oneOf(mockedDependency).isTheSpecialWeek();
                    will(returnValue(false));
            }
        });

        DiscountManager discountManager = new DiscountManager(isDiscountsSeason, mockedDependency);

        // Act
        double actual=discountManager.calculatePriceAfterDiscount(originalPrice);

        // Assert
        // make sure that mocking Expectations Is Satisfied
        mockingContext.assertIsSatisfied();

        // make sure that the actual value exactly equals the expected value
        assertEquals(expectedPrice, actual, 0.01);
    }

    //boundary tests

    //test when price is max double value and isTheSpecialWeek() is false, and getDiscountPercentage() has the week number as even
    @Test
    public void testCalculatePriceWhenPriceIsMAX() throws Exception {
        // Arrange
        boolean isDiscountsSeason = true;
        double originalPrice = Double.MAX_VALUE;
        double expectedPrice = originalPrice * 0.07;

        //mocking obj
        Mockery mockingContext = new Mockery();

        //mocked class
        IDiscountCalculator mockedDependency = mockingContext.mock(IDiscountCalculator.class);
        mockingContext.checking(new Expectations(){
            {
                oneOf(mockedDependency).isTheSpecialWeek();
                    will(returnValue(false));
                oneOf(mockedDependency).getDiscountPercentage();
                    will(returnValue(7));
            }
        });
        DiscountManager discountManager = new DiscountManager(isDiscountsSeason, mockedDependency);
        // Act
        double actual=discountManager.calculatePriceAfterDiscount(originalPrice);

        // Assert
        // make sure that mocking Expectations Is Satisfied
        mockingContext.assertIsSatisfied();

        // make sure that the actual value exactly equals the expected value
        assertEquals(expectedPrice, actual, originalPrice * 0.00001);
    }


    @Test
    public void testCalculatePriceWhenPriceIsMinimum() {
        boolean isDiscountsSeason = true;
        double originalPrice = 0.01;

        Mockery context = new Mockery();
        IDiscountCalculator mockedDependency = context.mock(IDiscountCalculator.class);

        context.checking(new Expectations() {{
            oneOf(mockedDependency).isTheSpecialWeek(); will(returnValue(true));
            never(mockedDependency).getDiscountPercentage();
        }});

        DiscountManager manager = new DiscountManager(isDiscountsSeason, mockedDependency);

        double actual = manager.calculatePriceAfterDiscount(originalPrice);
        double expected = originalPrice * 0.8;

        context.assertIsSatisfied();
        assertEquals(expected, actual, 0.0001);
    }

}
