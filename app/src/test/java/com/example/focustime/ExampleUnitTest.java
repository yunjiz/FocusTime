package com.example.focustime;

import com.example.focustime.util.Utility;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void TestTimeFormat() {
        assertEquals("2h46m40s", Utility.formatElapseTime(10000000));
    }

    @Test
    public void TestCalendarAddMonth() throws ParseException {

        Calendar c = Calendar.getInstance();
        c.set(2020,0,1, 0, 0, 0);
        Date date = c.getTime();
        Date nextDate = Utility.getNextMonthDate(date);
        assertEquals("Wed Jan 01 00:00:00 PST 2020", date.toString());
        assertEquals("Sat Feb 01 00:00:00 PST 2020", nextDate.toString());
    }
}