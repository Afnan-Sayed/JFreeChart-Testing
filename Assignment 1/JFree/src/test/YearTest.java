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
    //below minimum (-10000)
    @Test
    (expected = IllegalArgumentException.class)
    public void testIntConstructor_YearBelowMinimum() {
        new Year(-10000);  // Should throw exception
    }
    //above maximum (10000).
    @Test(expected = IllegalArgumentException.class)
    public void testIntConstructor_YearAboveMaximum() {
        new Year(10000);  // Should throw exception
    }
    @Test
    public void testDateConstructor_ValidDate() {
        // Create a date for July 15, 2020
        Calendar cal = Calendar.getInstance();
        cal.set(2020, Calendar.JULY, 15);
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

    /*@Test
    public void testDateCalendarConstructor() {
        // Create date for April 15, 2005
        Calendar dateCal = Calendar.getInstance();
        dateCal.set(2005, Calendar.APRIL, 15);
        Date testDate = dateCal.getTime();

        // Use a different timezone for calculations
        Calendar calculationCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.US);
//no cons for it
        Year year = new Year(testDate,calculationCal);
        assertEquals(2005, year.getYear());
    }*/
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
