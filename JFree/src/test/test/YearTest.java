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



////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////// previous() ///////////////////////////////////////
/*
    characteristic 1: 1900<year<=9999
        b1: yes                                  -> c11  //new Year(this.year - 1)
        b2: no (when year<=1900 or year>9999)    -> c12  //yetla3 null
     */
    @Test
    public void testPreviousWhenYearIsValid1()
    {
        int y= 1901;
        Year year =new Year(y);

        //as year extends RegularTimePeriod and the req type is RegularTimePeriod, so we cast to (Year)
        Year actual = (Year) year.previous();
        assertEquals(1900, actual.getYear());
    }
    @Test
    public void testPreviousWhenYearIsValid2()
    {
        int y= 9999;
        Year year =new Year(y);

        //as year extends RegularTimePeriod and the req type is RegularTimePeriod, so we cast to (Year)
        Year actual = (Year) year.previous();
        assertEquals(9998, actual.getYear());
    }

    @Test
    public void testPreviousWhenYearIsValid3()
    {
        int y= 1920;
        Year year =new Year(y);

        //as year extends RegularTimePeriod and the req type is RegularTimePeriod, so we cast to (Year)
        Year actual = (Year) year.previous();
        assertEquals(1919, actual.getYear());
    }

    @Test
    public void testPreviousWhenYearIsInValid1()
    {
        int y= 1900;
        Year year =new Year(y);

        //as year extends RegularTimePeriod and the req type is RegularTimePeriod, so we cast to (Year)
        Year actual = (Year) year.previous();
        assertNull(actual);
    }

    @Test
    public void testPreviousWhenYearIsInValid2()
    {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {new Year(1899);});
        assertEquals("Year constructor: year (1899) outside valid range.", exception.getMessage());
    }

    @Test
    public void testPreviousWhenYearIsInValid3()
    {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {new Year(10000);});
        assertEquals("Year constructor: year (10000) outside valid range.", exception.getMessage());
    }

////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////// next() ///////////////////////////////////////
    /*
    characteristic 1: 1900<=year<9999
        b1: yes                                  -> c11  //new Year(this.year + 1)
        b2: no (when year<1900 or year>=9999)    -> c12  //yetla3 null
     */
    @Test
    public void testNextWhenYearIsValid1()
    {
        int y= 1900;
        Year year =new Year(y);

        //as year extends RegularTimePeriod and the req type is RegularTimePeriod, so we cast to (Year)
        Year actual = (Year) year.next();
        assertEquals(1901, actual.getYear());
    }
    @Test
    public void testNextWhenYearIsValid2()
    {
        int y= 9998;
        Year year =new Year(y);

        //as year extends RegularTimePeriod and the req type is RegularTimePeriod, so we cast to (Year)
        Year actual = (Year) year.next();
        assertEquals(9999, actual.getYear());
    }

    @Test
    public void testNextWhenYearIsValid3()
    {
        int y= 1920;
        Year year =new Year(y);

        //as year extends RegularTimePeriod and the req type is RegularTimePeriod, so we cast to (Year)
        Year actual = (Year) year.next();
        assertEquals(1921, actual.getYear());
    }

    @Test
    public void testNextWhenYearIsInValid1()
    {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {new Year(1899);});
        assertEquals("Year constructor: year (1899) outside valid range.", exception.getMessage());
    }

    @Test
    public void testNextWhenYearIsInValid2()
    {
        int y= 9999;
        Year year =new Year(y);

        //as year extends RegularTimePeriod and the req type is RegularTimePeriod, so we cast to (Year)
        Year actual = (Year) year.previous();
        assertNull(actual);
    }

    @Test
    public void testNextWhenYearIsInValid3()
    {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {new Year(10000);});
        assertEquals("Year constructor: year (10000) outside valid range.", exception.getMessage());
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


//////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////// GetFirstMillisecond() ////////////////////////////////////
    //getFirstMillisecond(Calendar calendar)
/*
    characteristic 1: calendar is null
        b1: yes -> c11
        b2: no  -> c12

    characteristic 2: normal year
        b1: yes ->c21
        b2: no  ->c22

    characteristic 3: 1900<=year<=9999
        b1: Yes -> c31
        b2: No  -> c32

    characteristic 4: calendar passed with correct year
        b1: Yes (same year as object)                    -> c31
        b2: No (wrong year —> doesn't affect peg logic)  -> c32

    neglect:
     c11+c21+c31+c41
     c11+c21+c31+c42
     c11+c21+c32+c41
     c11+c21+c32+c42
     c11+c22+c31+c41
     c11+c22+c31+c42
     c11+c22+c32+c41
     c11+c22+c32+c42

  as no need to test them no combination when calendar is null with probability of having correct calendar

     c12+c21+c31+c41
     c12+c21+c31+c42
     c12+c21+c32+c41
     c12+c21+c32+c42
     c12+c22+c31+c41
     c12+c22+c31+c42
     c12+c22+c32+c41
     c12+c22+c32+c42

    so we have @least 8 test cases should be covered

     */


//c12+c21+c31+c41
@Test
public void testGetFirstMillisecond_NotNull_NormalYear_InRange_CorrectCalendar()
{
    Year year = new Year(2025); // normal year
    TimeZone tz = TimeZone.getTimeZone("Africa/Cairo");
    Calendar calendar = Calendar.getInstance(tz);

    //first millisecond of 2025
    calendar.set(2025, Calendar.JANUARY, 1, 0, 0, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    long expectedFirst = calendar.getTimeInMillis();

    long actual = year.getFirstMillisecond(calendar);
    assertEquals(expectedFirst, actual);
}


//c12+c21+c31+c42
@Test
public void testGetFirstMillisecond_NotNull_NormalYear_InRange_WrongCalendar()
{
    Year year = new Year(2025);
    TimeZone tz = TimeZone.getTimeZone("Africa/Cairo");

    Calendar wrongCalendar = Calendar.getInstance(tz);
    wrongCalendar.set(Calendar.YEAR, 2000);

    Calendar expectedCalendar = Calendar.getInstance(tz);
    expectedCalendar.set(2025, Calendar.JANUARY, 1, 0, 0, 0);
    expectedCalendar.set(Calendar.MILLISECOND, 0);
    long expectedFirst = expectedCalendar.getTimeInMillis();

    long actual = year.getFirstMillisecond(wrongCalendar);
    assertEquals(expectedFirst, actual);
}

//c12+c21+c32+c41
@Test
public void testGetFirstMillisecond_NotNull_NormalYear_LessThanMin_CorrectCalendar()
{
    Year year = new Year(1899);
    TimeZone tz = TimeZone.getTimeZone("Africa/Cairo");
    Calendar calendar = Calendar.getInstance(tz);
    assertThrows(IllegalArgumentException.class, () -> year.getFirstMillisecond(calendar));

}

//c12+c21+c32+c41
@Test
public void testGetFirstMillisecond_NotNull_NormalYear_GreaterThanMax_CorrectCalendar()
{
    Year year = new Year(10000);
    TimeZone tz = TimeZone.getTimeZone("Africa/Cairo");
    Calendar calendar = Calendar.getInstance(tz);

    assertThrows(IllegalArgumentException.class, () -> year.getFirstMillisecond(calendar));
}

//c12+c21+c32+c42
@Test
public void testGetFirstMillisecond_NotNull_NormalYear_GreaterThanMax_WrongCalendar()
{
    Year year = new Year(10000);
    TimeZone tz = TimeZone.getTimeZone("Africa/Cairo");
    Calendar calendar = Calendar.getInstance(tz);
    calendar.set(Calendar.YEAR, 2000);
    assertThrows(IllegalArgumentException.class, () -> year.getFirstMillisecond(calendar));
}

//c12+c21+c32+c42
    @Test
public void testGetFirstMillisecond_NotNull_NormalYear_LessThanMin_WrongCalendar()
{
    Year year = new Year(1899);
    TimeZone tz = TimeZone.getTimeZone("Africa/Cairo");
    Calendar calendar = Calendar.getInstance(tz);

    calendar.set(Calendar.YEAR, 2000);
    assertThrows(IllegalArgumentException.class, () -> year.getFirstMillisecond(calendar));

}

//c12+c22+c31+c41
    @Test
public void testGetFirstMillisecond_NotNull_LeapYear_InRange_CorrectCalendar()
{
    Year year = new Year(2024);
    TimeZone tz = TimeZone.getTimeZone("Africa/Cairo");
    Calendar calendar = Calendar.getInstance(tz);

    calendar.set(2024, Calendar.JANUARY, 1, 0, 0, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    long expectedFirst = calendar.getTimeInMillis();

    long actual = year.getFirstMillisecond(calendar);
    assertEquals(expectedFirst, actual);

}

//c12+c22+c31+c42
    @Test
public void testGetFirstMillisecond_NotNull_LeapYear_InRange_WrongCalendar()
{
    Year year = new Year(2024);
    TimeZone tz = TimeZone.getTimeZone("Africa/Cairo");

    Calendar wrongCalendar = Calendar.getInstance(tz);
    wrongCalendar.set(Calendar.YEAR, 2000);

    Calendar expectedCalendar = Calendar.getInstance(tz);
    expectedCalendar.set(2024, Calendar.JANUARY, 1, 0, 0, 0);
    expectedCalendar.set(Calendar.MILLISECOND, 0);
    long expectedFirst = expectedCalendar.getTimeInMillis();

    long actual = year.getFirstMillisecond(wrongCalendar);
    assertEquals(expectedFirst, actual);


}

//c12+c22+c32+c41
    @Test
public void testGetFirstMillisecond_NotNull_LeapYear_LessThanMin_CorrectCalendar()
{
    Year year = new Year(1896);
    TimeZone tz = TimeZone.getTimeZone("Africa/Cairo");
    Calendar calendar = Calendar.getInstance(tz);
    assertThrows(IllegalArgumentException.class, () -> year.getFirstMillisecond(calendar));
}

//c12+c22+c32+c41
    @Test
public void testGetFirstMillisecond_NotNull_LeapYear_GreaterThanMax_CorrectCalendar()
{
    Year year = new Year(10000);
    TimeZone tz = TimeZone.getTimeZone("Africa/Cairo");
    Calendar calendar = Calendar.getInstance(tz);
    assertThrows(IllegalArgumentException.class, () -> year.getFirstMillisecond(calendar));
}

//c12+c22+c32+c42
    @Test
public void testGetFirstMillisecond_NotNull_LeapYear_GreaterThanMax_WrongCalendar()
{
    Year year = new Year(10000);
    TimeZone tz = TimeZone.getTimeZone("Africa/Cairo");
    Calendar calendar = Calendar.getInstance(tz);

    calendar.set(Calendar.YEAR, 2000);
    assertThrows(IllegalArgumentException.class, () -> year.getFirstMillisecond(calendar));
}

//c12+c22+c32+c42
    @Test
public void testGetFirstMillisecond_NotNull_LeapYear_LessThanMin_WrongCalendar()
{
    Year year = new Year(1896);
    TimeZone tz = TimeZone.getTimeZone("Africa/Cairo");
    Calendar calendar = Calendar.getInstance(tz);

    calendar.set(Calendar.YEAR, 2000);
    assertThrows(IllegalArgumentException.class, () -> year.getFirstMillisecond(calendar));
}
}