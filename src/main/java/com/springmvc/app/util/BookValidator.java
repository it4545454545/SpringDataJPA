package com.springmvc.app.util;

import com.springmvc.app.dao.BookDAO;
import com.springmvc.app.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Component
public class BookValidator implements Validator {
    private final BookDAO bookDAO;
    private static final String DATE_FORMAT_REGEX = "^\\d{4}-\\d{2}-\\d{2}$"; // Паттерн для формата yyyy-MM-dd

    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;
        if(!isValidDateFormat(errors.getFieldError().getField())){
            errors.rejectValue("issueDate","","WRONG FORMAT");
        }


    }

    private boolean isValidDateFormat(String date) {
        Pattern pattern = Pattern.compile(DATE_FORMAT_REGEX);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }
}
