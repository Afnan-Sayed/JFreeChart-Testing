package test;

import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimePeriodFormatException;
import org.jfree.data.time.Year;
import org.junit.Test;

import static org.junit.Assert.*;
import java.util.Calendar;

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

    // Test getLastMillisecond()
    @Test
    public void testGetLastMillisecond() {
        Year year = new Year(2023);
        long lastMs = year.getLastMillisecond();

        // Verify it's not zero and within reasonable bounds
        assertTrue(lastMs > 0);

        // Verify it changes after pegging to a different calendar
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.JANUARY, 1);
        year.peg(cal);
        assertNotEquals(lastMs, year.getLastMillisecond());
    }

    @Test
    public void testGetLastMillisecondForDifferentYears() {
        Year year2020 = new Year(2020); // Leap year
        Year year2021 = new Year(2021); // Non-leap year

        long duration2020 = year2020.getLastMillisecond() - year2020.getFirstMillisecond();
        long duration2021 = year2021.getLastMillisecond() - year2021.getFirstMillisecond();

        // Leap year should be 1 day longer in milliseconds
        assertEquals(24 * 60 * 60 * 1000L, duration2020 - duration2021);
    }

    // Test equals()
    @Test
    public void testEqualsWithSameYear() {
        Year year1 = new Year(2023);
        Year year2 = new Year(2023);
        assertTrue(year1.equals(year2));
    }

    @Test
    public void testEqualsWithDifferentYears() {
        Year year1 = new Year(2023);
        Year year2 = new Year(2024);
        assertFalse(year1.equals(year2));
    }

    @Test
    public void testEqualsWithNull() {
        Year year = new Year(2023);
        assertFalse(year.equals(null));
    }

    @Test
    public void testEqualsWithDifferentClass() {
        Year year = new Year(2023);
        assertFalse(year.equals("2023"));
    }

    @Test
    public void testEqualsWithSameObject() {
        Year year = new Year(2023);
        assertTrue(year.equals(year));
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

    // Test compareTo()
    @Test
    public void testCompareToWithEarlierYear() {
        Year year2023 = new Year(2023);
        Year year2024 = new Year(2024);
        assertTrue(year2023.compareTo(year2024) < 0);
    }

    @Test
    public void testCompareToWithLaterYear() {
        Year year2023 = new Year(2023);
        Year year2022 = new Year(2022);
        assertTrue(year2023.compareTo(year2022) > 0);
    }

    @Test
    public void testCompareToWithSameYear() {
        Year year1 = new Year(2023);
        Year year2 = new Year(2023);
        assertEquals(0, year1.compareTo(year2));
    }

    @Test
    public void testCompareToWithNonYearObject() {
        Year year = new Year(2023);
        assertEquals(1, year.compareTo(new Object()));
    }

    // Test toString()
    @Test
    public void testToString() {
        Year year = new Year(2023);
        assertEquals("2023", year.toString());
    }

    @Test
    public void testToStringWithMinimumYear() {
        Year year = new Year(-9999);
        assertEquals("-9999", year.toString());
    }

    @Test
    public void testToStringWithMaximumYear() {
        Year year = new Year(9999);
        assertEquals("9999", year.toString());
    }

    // Test parseYear()
    @Test
    public void testParseYearValid() {
        Year year = Year.parseYear("2023");
        assertEquals(2023, year.getYear());
    }

    @Test
    public void testParseYearInvalidFormat() {
        assertThrows(TimePeriodFormatException.class, () -> {
            Year.parseYear("abcd");
        });
    }

    @Test
    public void testParseYearEmptyString() {
        assertThrows(TimePeriodFormatException.class, () -> {
            Year.parseYear("");
        });
    }

    @Test
    public void testParseYearNull() {
        assertThrows(TimePeriodFormatException.class, () -> {
            Year.parseYear(null);
        });
    }

    @Test
    public void testParseYearBelowMinimum() {
        assertThrows(TimePeriodFormatException.class, () -> {
            Year.parseYear("-10000");
        });
    }

    @Test
    public void testParseYearAboveMaximum() {
        assertThrows(TimePeriodFormatException.class, () -> {
            Year.parseYear("10000");
        });
    }

    @Test
    public void testParseYearWithWhitespace() {
        Year year = Year.parseYear(" 2023 ");
        assertEquals(2023, year.getYear());
    }
}