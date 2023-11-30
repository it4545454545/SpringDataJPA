package com.springjpa.app.util;

import com.springjpa.app.models.Book;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BookSearchTextValidator implements Validator {
    private static final String DATE_FORMAT_REGEX = "[A-Za-z0-9\\p{Punct} ]{2,10}";


    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        String searchText = ((Book) o).getTitle();
        if (!errors.hasErrors()) {
            if (!isValidDateFormat(searchText)) {
                errors.rejectValue("title", "", "Should be 2-10 symbols");
            }
        }
    }

    private boolean isValidDateFormat(String searchText) {
        Pattern pattern = Pattern.compile(DATE_FORMAT_REGEX);
        Matcher matcher = pattern.matcher(searchText);
        return matcher.matches();
    }

}