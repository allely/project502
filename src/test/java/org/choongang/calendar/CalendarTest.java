package org.choongang.calendar;

import org.choongang.Calendar.Calendar;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class CalendarTest {

//    @Test
//    void test1() {
//        Calendar cal = new Calendar();
//        int[] days = cal.getDays(2024,3);
//
//        System.out.println(Arrays.stream(days).toArray());
//    }

    @Test
    void test2() {
        Calendar cal = new Calendar();
        Map<String, Object> date = cal.getData(2024, 1);

        System.out.println(date);

    }
}
