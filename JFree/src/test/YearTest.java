package test;

import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.Year;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import java.util.Calendar;

public class YearTest {
    Year year;
    private Year year2000;
    private Year year2005;
    private Year year1995;

    @Before
    public void arrange() {
        year = new Year();
        year2000 = new Year(2000);
        year2005 = new Year(2005);
        year1995 = new Year(1995);
    }

    @Test
    public void testYearDefaultCtor() {
        arrange();
        assertEquals(2025, year.getYear());
    }

    // Test getLastMillisecond()

    @Test
    public void testLastMillisecond_StandardYear() {
        Calendar calendar = Calendar.getInstance();
        Year year = new Year(2023);
        calendar.set(2023, Calendar.DECEMBER, 31, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        long expected = calendar.getTimeInMillis();

        assertEquals(expected, year.getLastMillisecond(calendar));
    }

    @Test
    public void testLastMillisecond_LeapYear() {
        Calendar calendar = Calendar.getInstance();
        Year year = new Year(2020);
        calendar.set(2020, Calendar.DECEMBER, 31, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        long expected = calendar.getTimeInMillis();

        assertEquals(expected, year.getLastMillisecond(calendar));
    }

    @Test
    public void testLastMillisecond_EarlyYear() {
        Calendar calendar = Calendar.getInstance();
        Year year = new Year(1900);
        calendar.set(1900, Calendar.DECEMBER, 31, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        long expected = calendar.getTimeInMillis();

        assertEquals(expected, year.getLastMillisecond(calendar));
    }

    @Test
    public void testLastMillisecond_FutureYear() {
        Calendar calendar = Calendar.getInstance();
        Year year = new Year(3000);
        calendar.set(3000, Calendar.DECEMBER, 31, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        long expected = calendar.getTimeInMillis();

        assertEquals(expected, year.getLastMillisecond(calendar));
    }

    @Test(expected = NullPointerException.class)
    public void testLastMillisecond_NullCalendar() {
        Year year = new Year(2025);
        year.getLastMillisecond(null);
    }


    // Test equals()

    @Test
    public void testEquals_SameObject() {
        Year year = new Year(2025);
        assertTrue(year.equals(year));
    }

    @Test
    public void testEquals_SameValueDifferentObject() {
        Year year1 = new Year(2025);
        Year year2 = new Year(2025);
        assertTrue(year1.equals(year2));
    }

    @Test
    public void testEquals_DifferentYear() {
        Year year1 = new Year(2025);
        Year year2 = new Year(2024);
        assertFalse(year1.equals(year2));
    }

    @Test
    public void testEquals_NullObject() {
        Year year = new Year(2025);
        assertFalse(year.equals(null));
    }

    @Test
    public void testEquals_DifferentType() {
        Year year = new Year(2025);
        String notAYear = "2025";
        assertFalse(year.equals(notAYear));
    }

    @Test
    public void testEquals_BoundaryCase_MinValue() {
        Year year1 = new Year(1900);
        Year year2 = new Year(1900);
        assertTrue(year1.equals(year2));
    }

    @Test
    public void testEquals_BoundaryCase_MaxValue() {
        Year year1 = new Year(9999);
        Year year2 = new Year(9999);
        assertTrue(year1.equals(year2));
    }


    // Test compareTo()

    // Test case: Year is before another Year (Negative)
    @Test
    public void testCompareTo_Before() {
        // Comparing year1995 (before) to year2000
        int result = year1995.compareTo(year2000);
        assertTrue("1995 should be before 2000", result < 0);
    }

    // Test case: Year is after another Year (Positive)
    @Test
    public void testCompareTo_After() {
        // Comparing year2005 (after) to year2000
        int result = year2005.compareTo(year2000);
        assertTrue("2005 should be after 2000", result > 0);
    }

    // Test case: Year is the same as another Year (Zero)
    @Test
    public void testCompareTo_SameYear() {
        // Comparing year2000 (same) to year2000
        int result = year2000.compareTo(year2000);
        assertEquals("Years should be equal", 0, result);
    }

    // Edge case: Compare to a Year in the distant future (Positive)
    @Test
    public void testCompareTo_FutureYear() {
        Year year3000 = new Year(3000);
        int result = year2000.compareTo(year3000);
        assertTrue("2000 should be before 3000", result < 0);
    }

    // Edge case: Compare to a Year in the distant past (Negative)
    @Test
    public void testCompareTo_PastYear() {
        Year year1000 = new Year(1000);
        int result = year2000.compareTo(year1000);
        assertTrue("2000 should be after 1000", result > 0);
    }

    // Edge case: Comparing the same year object to itself (Zero)
    @Test
    public void testCompareTo_SameObject() {
        int result = year2000.compareTo(year2000); // Same object
        assertEquals("Same object comparison should return zero", 0, result);
    }

    // Edge case: Comparing to null (Expect NullPointerException)
    @Test
    public void testCompareTo_Null() {
        assertThrows(NullPointerException.class, () -> {
            year2000.compareTo(null);
        });
    }

    // Test hashCode()
    @Test
    public void testHashCodeConsistency() {
        Year year = new Year(2023);
        assertEquals(year.hashCode(), year.hashCode());
    }

    @Test
    public void testHashCodeEquality() {
        Year year1 = new Year(2023);
        Year year2 = new Year(2023);
        assertEquals(year1.hashCode(), year2.hashCode());
    }

    @Test
    public void testHashCodeInequality() {
        Year year1 = new Year(2023);
        Year year2 = new Year(2024);
        assertNotEquals(year1.hashCode(), year2.hashCode());
    }

    @Test
    public void testHashCodeValue() {
        Year year = new Year(2000);
        int expected = 31 * 17 + 2000;  // 31
        assertEquals(expected, year.hashCode());
    }



    // Test toString()
    @Test
    public void testToString() {
        Year year = new Year(2023);
        assertEquals("2023", year.toString());
    }

    @Test
    public void testToStringWithMinimumYear() {
        Year year = new Year(1900);
        assertEquals("1900", year.toString());
    }

    @Test
    public void testToStringWithMaximumYear() {
        Year year = new Year(9999);
        assertEquals("9999", year.toString());
    }


    // Test parseYear()

    @Test
    public void testValidYear() {
        Year year = Year.parseYear("2023");
        assertNotNull(year);
        assertEquals(2023, year.getYear());
    }

    @Test
    public void testParseYearInvalidFormat() {
        assertEquals(null,Year.parseYear("dkl"));
    }

    @Test
    public void testInvalidYearLength() {
        assertEquals(null,Year.parseYear("23"));
    }

    @Test
    public void testNonNumericYear() {
        assertNull(Year.parseYear("202A"));
    }

    @Test
    public void testEmptyString() {
        assertNull(Year.parseYear(""));
    }

    @Test
    public void testZeroYear() {
        assertNotNull(Year.parseYear("0000"));
        assertEquals(0, Year.parseYear("0000").getYear());
    }

    @Test
    public void testNullInput() {
        assertNull(Year.parseYear(null));
    }

    @Test
    public void testParseYearWithWhitespace() {
        Year year = Year.parseYear(" 2023 ");
        assertEquals(2023, year.getYear());
    }


//    @Test(expected = TimePeriodFormatException.class)
//    public void testInvalidString() {
//        Year.parseYear("abcdfff");
//    }


}