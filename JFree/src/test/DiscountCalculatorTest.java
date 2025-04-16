package test;

import JFree.DiscountCalculator;
import org.jfree.data.time.Week;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class DiscountCalculatorTest {

    @Test
    public void testIsTheSpecialWeekWhenFalse() throws Exception {
        // Arrange
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.MARCH, 22);  // March 22, 2025
        Date date = calendar.getTime();
        Week week = new Week(date);

        // Act
        DiscountCalculator calculatorToTest = new DiscountCalculator(week);

        // Assert
        assertFalse(calculatorToTest.isTheSpecialWeek());
    }

    @Test
   public void testIsTheSpecialWeekWhenTrue() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.JUNE, 23); // June 23, 2025 falls in week 26
        Week week = new Week(calendar.getTime());

        DiscountCalculator calculator = new DiscountCalculator(week);

        assertTrue(calculator.isTheSpecialWeek());
   }

    @Test
    public void testGetDiscountPercentageEvenWeek() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.JUNE, 23); // week 26
        Week week = new Week(calendar.getTime());

        DiscountCalculator calculator = new DiscountCalculator(week);

        assertEquals(7, calculator.getDiscountPercentage());
    }


    @Test
    public void testGetDiscountPercentageOddWeek() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.JUNE, 30); // week 27
        Week week = new Week(calendar.getTime());

        DiscountCalculator calculator = new DiscountCalculator(week);

        assertEquals(5, calculator.getDiscountPercentage());
    }


    @Test
    public void testIsTheSpecialWeekWithWrongDate() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.JUNE, 20); // Before week 26 starts
        Week week = new Week(calendar.getTime());

        DiscountCalculator calculator = new DiscountCalculator(week);

        assertFalse(calculator.isTheSpecialWeek()); // Should be false
    }

    @Test(expected = NullPointerException.class)
  //  Purpose: This tells JUnit to expect a NullPointerException to be thrown during the execution of the test. If the NullPointerException is thrown at any point during the execution of the test, the test will pass. If the exception is not thrown (or if a different exception is thrown), the test will fail.
  //  How it works: The test will continue executing until it reaches a line where the exception is expected. If that exception occurs, JUnit considers the test successful. If the exception doesn't happen, JUnit will fail the test and indicate that it was expecting an exception but none was thrown.
    public void testWithNullWeek() {
        DiscountCalculator calculator = new DiscountCalculator(null);
        calculator.isTheSpecialWeek();
    }

    // Test missing cases ( JUNE, 23 is a date in week 26 )

}
