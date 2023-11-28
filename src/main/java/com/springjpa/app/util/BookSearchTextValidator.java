package com.springjpa.app.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookSearchTextValidator implements Validator {
//    private static final String DATE_FORMAT_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";


    @Override
    public boolean supports(Class<?> aClass) {
        return String.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        String  searchText = (String) o;
        if (!errors.hasErrors()) {

                errors.rejectValue("", "", "FFFFFFFFFE");

        }
    }



}