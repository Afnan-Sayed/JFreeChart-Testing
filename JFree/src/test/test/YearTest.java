package test;

import org.jfree.data.time.Year;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

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

    TODO: CHECK BOUNDARIES
    TODO: test each characteristic alone
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


////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////// Peg(Calendar calendar) ////////////////////////////////////
/*
Recalculates the start date/time and end date/time for this time period relative to the supplied calendar (which incorporates a time zone).
Parameters: calendar - the calendar (null not permitted).

Peg(Calendar calendar)
 {
    this.firstMillisecond = this.getFirstMillisecond(calendar);
    this.lastMillisecond = this.getLastMillisecond(calendar);
 }

    characteristic 1: calendar is null
        b1: yes -> c11
        b2: no  -> c12

    characteristic 2: normal year
        b1: yes ->c21
        b2: no  ->c22

    characteristic 3: calendar passed with correct year
        b1: Yes (same year as object)                    -> c31
        b2: No (wrong year —> doesn't affect peg logic)  -> c32

    neglect c11+c21+c31, c11+c21+c32, c11+c22+c31 and c11+c22+c32 as no need to test them
    so we have 4 test cases should be covered

    TODO: CHECK BOUNDARIES
    TODO: CHECK each characteristic alone
     */

    @Test
    public void testPegNotNullCalAndNormalYearAndCorrectYear()
    {
        year = new Year(2025); //year=2025
        TimeZone tz = TimeZone.getTimeZone("Africa/Cairo");
        Calendar calendar = Calendar.getInstance(tz);

        year.peg(calendar);

        //last millisecond
        //cal is 2025 same as year -> correct year
        calendar.set(2025, Calendar.DECEMBER, 31, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        long expectedLast = calendar.getTimeInMillis();
        assertEquals(expectedLast, year.getLastMillisecond());

        //first millisecond
        calendar.set(2025, Calendar.JANUARY, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long expectedFirst = calendar.getTimeInMillis();
        assertEquals(expectedFirst, year.getFirstMillisecond());
    }


    @Test
    public void testPegNotNullAndNormalYearAndWrongYear()
    {
        Year year = new Year(2025); //normal year
        TimeZone tz = TimeZone.getTimeZone("Africa/Cairo");
        Calendar calendar = Calendar.getInstance(tz);

        //wrong year -> should not affect result as it will take 2025 defined in constructor
        calendar.set(Calendar.YEAR, 2000);
        year.peg(calendar);

        //last millisecond
        calendar.set(2025, Calendar.DECEMBER, 31, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        long expectedLast = calendar.getTimeInMillis();
        assertEquals(expectedLast, year.getLastMillisecond());


        //first millisecond
        calendar.set(2025, Calendar.JANUARY, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long expectedFirst = calendar.getTimeInMillis();
        assertEquals(expectedFirst, year.getFirstMillisecond());

    }

    @Test
    public void testPegNotNullAndLeapYearAndCorrectYear()
    {
        year = new Year(2024); //year=2024, leap
        TimeZone tz = TimeZone.getTimeZone("Africa/Cairo");
        Calendar calendar = Calendar.getInstance(tz);

        year.peg(calendar);

        //last millisecond
        //cal is 2024 same as year -> correct year
        calendar.set(2024, Calendar.DECEMBER, 31, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        long expectedLast = calendar.getTimeInMillis();
        assertEquals(expectedLast, year.getLastMillisecond());

        //first millisecond
        calendar.set(2024, Calendar.JANUARY, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long expectedFirst = calendar.getTimeInMillis();
        assertEquals(expectedFirst, year.getFirstMillisecond());
    }

    @Test
    public void testPegNotNullAndLeapYearAndWrongYear(){
        Year year = new Year(2024); //leap year
        TimeZone tz = TimeZone.getTimeZone("Africa/Cairo");
        Calendar calendar = Calendar.getInstance(tz);

        //wrong year -> should not affect result as it will take 2025 defined in constructor
        calendar.set(Calendar.YEAR, 2000);
        year.peg(calendar);

        //last millisecond
        calendar.set(2024, Calendar.DECEMBER, 31, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        long expectedLast = calendar.getTimeInMillis();
        assertEquals(expectedLast, year.getLastMillisecond());


        //first millisecond
        calendar.set(2024, Calendar.JANUARY, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long expectedFirst = calendar.getTimeInMillis();
        assertEquals(expectedFirst, year.getFirstMillisecond());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////// previous() ///////////////////////////////////////
/*
@return The year preceding this one (or {@code null} if the current year is -9999).
/*

    characteristic 1: year > -9999 and !=0
        b1: yes                   -> c11  //new Year(this.year - 1)
        b2: no (when year<=-9999) -> c12  //yetla3 null

    characteristic 2: year =0
        b1: yes                   -> c21  //new Year(this.year - 1)


    so we have 4 test cases should be covered
     */
    @Test
    public void testPreviousWhenYearIsValidAndNotEqualZero()
    {
        int y= -9998;
        Year year =new Year(y);

        //as year extends RegularTimePeriod and the req type is RegularTimePeriod, so we cast to (Year)
        Year actual = (Year) year.previous();
        assertEquals(-9999, actual.getYear());
    }
    @Test
    public void testPreviousWhenYearIsValidAndEqualsZero()
    {
        int y= 0;
        Year year =new Year(y);

        //as year extends RegularTimePeriod and the req type is RegularTimePeriod, so we cast to (Year)
        Year actual = (Year) year.previous();
        assertEquals(-1, actual.getYear());
    }

    @Test
    public void testPreviousWhenYearIsInValid1()
    {
        int y= -9999;
        Year year =new Year(y);

        //as year extends RegularTimePeriod and the req type is RegularTimePeriod, so we cast to (Year)
        Year actual = (Year) year.previous();
        assertNull(actual);
    }

    @Test
    public void testPreviousWhenYearIsInValid2()
    {
        int y = -10000;
        Year year =new Year(y);

        //as year extends RegularTimePeriod and the req type is RegularTimePeriod, so we cast to (Year)
        Year actual = (Year) year.previous();
        assertNull(actual);
    }


////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////// next() ///////////////////////////////////////

    /*
 public RegularTimePeriod next() {return this.year < 9999 ? new Year(this.year + 1) : null;}
 The year following this one (or null if the current year is 9999).


    characteristic 1: year < 9999 and !=0
        b1: yes                   -> c11  //new Year(this.year + 1)
        b2: no (when year>=9999) -> c12  //yetla3 null

    characteristic 2: year =0
        b1: yes                   -> c21  //new Year(this.year + 1)

    so we have 4 test cases should be covered
     */
@Test
public void testNextWhenYearIsValidAndNotEqualZero()
{
    int y= 9998;
    Year year =new Year(y);

    //as year extends RegularTimePeriod and the req type is RegularTimePeriod, so we cast to (Year)
    Year actual = (Year) year.next();
    assertEquals(9999, actual.getYear());
}
    @Test
    public void testNextWhenYearIsValidAndEqualsZero()
    {
        int y= 0;
        Year year =new Year(y);

        //as year extends RegularTimePeriod and the req type is RegularTimePeriod, so we cast to (Year)
        Year actual = (Year) year.next();
        assertEquals(1, actual.getYear());
    }

    @Test
    public void testNextWhenYearIsInValid1()
    {
        int y= 9999;
        Year year =new Year(y);

        //as year extends RegularTimePeriod and the req type is RegularTimePeriod, so we cast to (Year)
        Year actual = (Year) year.next();
        assertNull(actual);
    }

    @Test
    public void testNextWhenYearIsInValid2()
    {
        int y = 10000;
        Year year =new Year(y);

        //as year extends RegularTimePeriod and the req type is RegularTimePeriod, so we cast to (Year)
        Year actual = (Year) year.next();
        assertNull(actual);
    }

/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////// GetSerialIndex() ////////////////////////////////////
/*
Returns a serial index number for the year.
The implementation simply returns the year number (e.g. 2002).

    characteristic 1: 9999>=year>=1900
        b1: yes                               -> c11  //return year
        b2: no (when year>9999 or year<1900)  -> c12  //throw new IllegalArgumentException("Year constructor: year (" + year + ") outside valid range.");

//lma ykon benhom
//2 boundaries
//greater that 9999
less than 1900
    so we have 5 test cases should be covered
*/

    @Test
    public void testGetSerialIndexInRange(){
        Year year = new Year(2000);
        assertEquals(2000, year.getSerialIndex());
    }

    @Test
    public void testGetSerialIndexBoundary1(){
        Year year = new Year(9999);
        assertEquals(9999, year.getSerialIndex());
    }

    @Test
    public void testGetSerialIndexBoundary2(){
        Year year = new Year(1900);
        assertEquals(1900, year.getSerialIndex());
    }

    @Test
    public void testGetSerialIndexOutBound1()
    {
        IllegalArgumentException exception = assertThrows (IllegalArgumentException.class, () -> new Year(10000));
        assertEquals("Year constructor: year (10000) outside valid range.", exception.getMessage());
    }


    @Test
    public void testGetSerialIndexOutBound2_WithMessage()
    {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {new Year(1899);});
        assertEquals("Year constructor: year (1899) outside valid range.", exception.getMessage());
    }


/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////// GetFirstMillisecond() ////////////////////////////////////
    //getFirstMillisecond(Calendar calendar)
    @Test
    public void testGetFirstMillisecond(){}
}