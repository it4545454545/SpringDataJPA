package com.springmvc.app.util;

import com.springmvc.app.dao.BookDAO;
import com.springmvc.app.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BookValidator implements Validator {
//    private final BookDAO bookDAO;
//    private static final String DATE_FORMAT_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";

//    @Autowired
//    public BookValidator(BookDAO bookDAO) {
//        this.bookDAO = bookDAO;
//    }

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
