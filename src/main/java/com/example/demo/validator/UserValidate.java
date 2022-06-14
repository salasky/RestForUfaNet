package com.example.demo.validator;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserValidate {

    private Pattern namePatter = Pattern.compile("^[a-zA-Z]*$");
    private Pattern phonePatter = Pattern.compile("^[0-9]*$");
    private Pattern emailPatter = Pattern.compile("\\b[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,4}\\b");


    public boolean isValidName(String name) {
        return null == name ? false : namePatter.matcher(name).find();
    }


    public boolean isValidPhoneNumber(String phone) {
        return null == phone ? false : phonePatter.matcher(phone).find();
    }

    public boolean isValidEmail(String email) {
        return null == email ? false : emailPatter.matcher(email).find();
    }


}
