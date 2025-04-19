package test;

import org.jfree.data.time.Year;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

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

//////////////////////////////// getLastMillisecond() ////////////////////////////////////

    /*
    Returns last millisecond of the year:

    characteristic 1:
        b1: relative to the time zone specified in the constructor,
        b2: in the calendar instance passed in the most recent call to the peg(Calendar) method. (y3ne regardless tz in constr. use the one in the passed cal)

    characteristic 2:
        b1: normal year
        b2: leap year
    so we have 4 test cases must be covered
     */

////RelativeToTimeZoneInConstructor and normal year
    @Test
    public void testGetLastMillisecondRelativeToTimeZoneInConstructorAndIsNormalYear(){
        //did not use .getdefault() as it produces different time zones according to which device is
        //the code running on, and unit testing must produce the same output each time
        TimeZone tz = TimeZone.getTimeZone("Africa/Cairo"); //verified mn java
        Locale locale = new Locale("ar", "EG");

        //badelo ay date el mohm ykon fe nafs el year ele b3mlha test '2025'
        Date date = new GregorianCalendar(2025, Calendar.MAY, 17).getTime();
        Year year = new Year(date, tz, locale);

        long actual = year.getLastMillisecond(); //bygeb las milli second fe 2025

        Calendar cal = Calendar.getInstance(tz, locale);
        cal.set(2025, Calendar.DECEMBER, 31, 23, 59, 59);
        cal.set(Calendar.MILLISECOND, 999);
        long expected = cal.getTimeInMillis();

        assertEquals(expected, actual);
    }

////RelativeToTimeZoneInConstructor and leap year
    @Test
    public void testGetLastMillisecondRelativeToTimeZoneInConstructorAndIsLeapYear() {
    //did not use .getdefault() as it produces different time zones according to which device is
    //the code running on, and unit testing must produce the same output each time
    TimeZone tz = TimeZone.getTimeZone("Africa/Cairo"); //verified mn java
    Locale locale = new Locale("ar", "EG");

    //badelo ay date el mohm ykon fe nafs el year ele b3mlha test '2025'
    Date date = new GregorianCalendar(2024, Calendar.MAY, 17).getTime();
    Year year = new Year(date, tz, locale);

    long actual = year.getLastMillisecond(); //bygeb las milli second fe 2025

    Calendar cal = Calendar.getInstance(tz, locale);
    cal.set(2024, Calendar.DECEMBER, 31, 23, 59, 59);
    cal.set(Calendar.MILLISECOND, 999);
    long expected = cal.getTimeInMillis();

    assertEquals(expected, actual);
}

////RelativeToTimeZoneInCalendarInPeg and normal year
    @Test
    public void testGetLastMillisecondRelativeToTimeZoneInCalendarInPegAndIsNormalYear() {
       arrange();

        TimeZone tz = TimeZone.getTimeZone("Africa/Cairo"); //verified mn java
        Calendar cal = Calendar.getInstance(tz);
        year.peg(cal);

        long actual = year.getLastMillisecond();

        cal.set(2025, Calendar.DECEMBER, 31, 23, 59, 59);
        cal.set(Calendar.MILLISECOND, 999);
        long expected = cal.getTimeInMillis();

        assertEquals(expected, actual);
    }

////RelativeToTimeZoneInCalendarInPeg and leap year
    @Test
    public void testGetLastMillisecondRelativeToTimeZoneInCalendarInPegAndIsLeapYear() {
    arrange();

    TimeZone tz = TimeZone.getTimeZone("Africa/Cairo"); //verified mn java
    Calendar cal = Calendar.getInstance(tz);
    year.peg(cal);

    long actual = year.getLastMillisecond();

    cal.set(2024, Calendar.DECEMBER, 31, 23, 59, 59);
    cal.set(Calendar.MILLISECOND, 999);
    long expected = cal.getTimeInMillis();

    assertEquals(expected, actual);
}

//////////////////////////////////////////////////////////////////////////////
//Peg(Calendar calendar)
@Test
public void testPeg(){}

    @Test
    public void testPrevious(){}

    @Test
    public void testNext(){}

    @Test
    public void testGetSerialIndex(){}

    //getFirstMillisecond(Calendar calendar)
    @Test
    public void testGetFirstMillisecond(){}
}
