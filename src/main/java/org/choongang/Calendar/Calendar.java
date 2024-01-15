package org.choongang.Calendar;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Lazy   //
@Component  //
public class Calendar {

    /**
     * 달력 데이터
     * 7 * 6 (42), 7 * 5 (35)
     * @param year
     * @param month
     */
    /**
     * 달력 데이터
     * 7 * 6 (42), 7 * 5(35)
     * @param year
     * @param month
     */
    public Map<String, Object> getData(int year, int month) {

        if(year == 0 && month == 0) { // 년도와 월 값이 없으면 현재 년도, 월로 고정
            LocalDate today = LocalDate.now();
            year = today.getYear();
            month = today.getMonthValue();
        }
        LocalDate sdate = LocalDate.of(year, month, 1);
        LocalDate eDate = sdate.plusMonths(1L).minusDays(1);
        int sYoil = sdate.getDayOfWeek().getValue(); // 매월 1일 요일

        sYoil = sYoil == 7 ? 0 : sYoil;

        int start = sYoil * -1;

        int cellNum = sYoil + eDate.getDayOfMonth() > 35 ? 42 : 35;


        Map<String, Object> data = new HashMap<>();

        List<Integer> days = new ArrayList<>(); // 날짜, 1, 2, 3,
        List<String> dates = new ArrayList<>(); // 날짜 문자열 2024-01-12
        List<Integer> yoils = new ArrayList<>(); // 요일 정보

        for (int i = start; i < cellNum + start; i++) {
            LocalDate date = sdate.plusDays(i);

            int yoil = date.getDayOfWeek().getValue();
            yoil = yoil == 7 ? 0 : yoil; // 0 ~ 6 (일 ~ 토)
            days.add(date.getDayOfMonth());
            dates.add(date.toString());
            yoils.add(yoil);

            data.put("days", days);
            data.put("dates", dates);
            data.put("yoils", yoils);
        }

        // 이전 달 년도, 월
        LocalDate prevMonthDate = sdate.minusMonths(1L);
        data.put("prevMonth", prevMonthDate.getYear()); // 이전 달 년도
        data.put("prevMonth", prevMonthDate.getMonthValue());   // 다음 달 월

        // 다음 달 년도, 월
        LocalDate nextMonthDate = sdate.plusMonths(1L);
        data.put("nextYear", nextMonthDate.getYear());
        data.put("nextMonth", nextMonthDate.getMonthValue());

        // 현재 년도, 월
        data.put("year", year);
        data.put("month", month);

        return data;
    }

    public Map<String, Object> getData() {


        return getData(0, 0);
    }
}
