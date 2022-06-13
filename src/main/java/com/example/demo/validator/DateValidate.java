package com.example.demo.validator;

import org.springframework.stereotype.Component;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

@Component
public class DateValidate {

    //Примеры допустимых дат:
    //2017-12-31
    //2020-02-29

    //полный GregorianDateMatcher c 1900 по 2999 год
    private Pattern DATETIME_PATTERN = Pattern.compile(
            "^((2000|2400|2800|(19|2[0-9])(0[48]|[2468][048]|[13579][26]))-02-29)$"
                    + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
                    + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
                    + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))\s[0-9][0-9]:[0-9][0-9]$");
    private Pattern DATE_PATTERN = Pattern.compile(
            "^((2000|2400|2800|(19|2[0-9])(0[48]|[2468][048]|[13579][26]))-02-29)$"
                    + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
                    + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
                    + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$");


    public boolean isValidDate(String date) {
        return null == date ? false : DATE_PATTERN.matcher(date).matches();
    }

    public boolean isValidDateTime(String datetime){
        return null == datetime ? false : DATETIME_PATTERN.matcher(datetime).matches();
    }

}
