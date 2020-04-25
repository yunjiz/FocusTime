package com.example.focustime;

import com.example.focustime.util.Utility;

import org.junit.Test;

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
    public void TestTimeFormat(){
        assertEquals("2h46m40s", Utility.formatElapseTime(10000000));
    }
}