package test;

import org.jfree.data.time.Year;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Locale;

public class YearTest {
    Year year;

    private void arrange() {
        year = new Year();
    }
    @Test
    public void testYearDefaultCtor() {
        arrange();
        assertEquals(2025, year.getYear());
    }


    //valid input
    @Test
    public void testIntConstructor_ValidYear() {
        Year year = new Year(2023);
        assertEquals(2023, year.getYear());
    }

    @Test
    public void testConstructorAtLowerBound() {
        Year y = new Year(1900);
        assertEquals(1900, y.getYear());}
    @Test
    public void testConstructorAtUpperBound() {
        Year y = new Year(9999);
        assertEquals(9999, y.getYear());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorAboveUpperBound() {
        new Year(10000);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorBelowLowerBound() {
        new Year(1899);
    }
   
    @Test
    public void testDateConstructor_ValidDate() {
        // Create a date for July 18, 2020
        Calendar cal = Calendar.getInstance();
        cal.set(2020, Calendar.JULY, 18);
        Date testDate = cal.getTime();

        Year year = new Year(testDate);
        assertEquals(2020, year.getYear());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDateConstructor_NullDate() {
        new Year((Date) null);  // Should throw exception
    }
    @Test
    public void testDateTimezoneLocaleConstructor() {
        // Create date for December 31, 2010
        Calendar cal = Calendar.getInstance();
        cal.set(2010, Calendar.DECEMBER, 31);
        Date testDate = cal.getTime();

        // Use US locale and default timezone
        Year year = new Year(testDate, TimeZone.getDefault(), Locale.US);
        assertEquals(2010, year.getYear());
    }
    @Test(expected = NullPointerException.class)
    public void testConstructorFromDateZoneLocaleNull() {
        new Year(null, TimeZone.getDefault(), Locale.getDefault());
    }

    @Test
    public void testGetYear() {
        Year year = new Year(2025);
        assertEquals(2025, year.getYear());
    }
    @Test
    public void testGetFirstMillisecond() {
        Year year = new Year(2020);
        Calendar cal = Calendar.getInstance();

        // Calculate expected value: Jan 1, 2020 00:00:00.000
        cal.set(2020, Calendar.JANUARY, 1, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long expected = cal.getTimeInMillis();

        // Verify method returns correct value
        assertEquals(expected, year.getFirstMillisecond(cal));
    }
}
