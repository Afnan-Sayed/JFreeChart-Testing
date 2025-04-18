package test;

import JFree.DiscountCalculator;
import org.jfree.data.time.Week;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class DiscountCalculatorTest {

    // isTheSpecialWeek() tests
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
    public void testIsTheSpecialWeekWithWrongDate() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.JUNE, 20); // Before week 26 starts
        Week week = new Week(calendar.getTime());

        DiscountCalculator calculator = new DiscountCalculator(week);

        assertFalse(calculator.isTheSpecialWeek()); // Should be false
    }

    // Edge cases

    @Test
    public void testWeirdDateLikeLeapYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.FEBRUARY, 29); // Valid leap year date
        Week week = new Week(calendar.getTime());

        DiscountCalculator calculator = new DiscountCalculator(week);
        assertNotNull(calculator.getDiscountPercentage()); // It should still behave normally
    }

    @Test
    public void testVeryOldDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1900, Calendar.JANUARY, 1);
        Week week = new Week(calendar.getTime());

        DiscountCalculator calculator = new DiscountCalculator(week);
        int discount = calculator.getDiscountPercentage();
        assertTrue(discount == 5 || discount == 7); // Week might be odd or even
    }

    @Test
    public void testFarFutureDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(3000, Calendar.DECEMBER, 31);
        Week week = new Week(calendar.getTime());

        DiscountCalculator calculator = new DiscountCalculator(week);
        int discount = calculator.getDiscountPercentage();
        assertTrue(discount == 5 || discount == 7);
    }


    // Discount percentage tests

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


    // Boundary cases
    @Test
    public void getDiscountPercentage_ShouldWork_ForFirstWeek() {
        Week firstWeek = new Week(1, 2023);
        DiscountCalculator calculator = new DiscountCalculator(firstWeek);
        assertEquals(5, calculator.getDiscountPercentage());  // Week 1 is odd
    }


    @Test
    public void getDiscountPercentage_ShouldWork_ForLastWeek() {
        Week lastWeek = new Week(53, 2023);  // Some years have 53 weeks
        DiscountCalculator calculator = new DiscountCalculator(lastWeek);
        assertEquals(5, calculator.getDiscountPercentage());  // Week 53 is odd
    }

    // Constructor tests

    @Test
    public void testNullWeekThrowsNPE() {
        assertThrows(NullPointerException.class, () -> {
            new DiscountCalculator(null); // Constructor rejects null
        });
    }

    //    @Test
//    public void NullValueGet(){
//        DiscountCalculator calculator = new DiscountCalculator(null);
//        assertThrows(NullPointerException.class, () -> {
//            calculator.getDiscountPercentage();
//        });
//    }


    // Test missing cases ( JUNE, 23 is a date in week 26 )

}
