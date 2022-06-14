package com.example.demo.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@ShellComponent
public class HolidayShell {

    public  static List<String> holidayList=new ArrayList<>();

    @ShellMethod("Добавление праздничных дней")
    public String addholiday(String date) {
        if (!isValidDate(date)){
            return "Неверная дата\n" +
                    "Формат записи даты YYYY-MM-DD";
        }
        holidayList.add(date);
        return date+ " добавлен в список праздничных";
    }

    @ShellMethod("Показать праздничные дни")
    public List showholiday() {
        return holidayList;
    }


    private Pattern DATE_PATTERN = Pattern.compile(
            "^((2000|2400|2800|(19|2[0-9])(0[48]|[2468][048]|[13579][26]))-02-29)$"
                    + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
                    + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
                    + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$");


    public boolean isValidDate(String date) {
        return null == date ? false : DATE_PATTERN.matcher(date).matches();
    }
}
