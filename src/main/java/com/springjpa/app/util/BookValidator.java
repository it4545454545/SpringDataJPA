package com.springjpa.app.util;

import com.springjpa.app.models.Book;
import org.springframework.beans.TypeMismatchException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Objects;

@Component
public class BookValidator implements Validator {
//    private static final String DATE_FORMAT_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";


@Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
//        Book book = (Book) o;
        if (!errors.hasErrors()) {
            if (!isValidDateFormat((String) errors.getFieldValue("issueDate"))) {
                errors.rejectValue("issueDate", "", "Invalid date. Also, the format must be 2022-01-09 (year-month-day)");
            }
        }
    }

    private boolean isValidDateFormat(String date) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException ex) {
            return false;
        }
//        Pattern pattern = Pattern.compile(DATE_FORMAT_REGEX);
//        Matcher matcher = pattern.matcher(date);
//        return matcher.matches();
        return true;
    }
}