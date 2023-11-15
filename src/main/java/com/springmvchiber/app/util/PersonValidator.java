package com.springmvchiber.app.util;

import com.springmvchiber.app.models.Person;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author Ivan L
 */
@Component
public class PersonValidator implements Validator {
    //    private static final String DATE_FORMAT_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";
//    private final PersonDAO personDAO;
//    @Autowired
//    public PersonValidator(PersonDAO personDAO) {
//        this.personDAO = personDAO;
//    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
//        Person person = (Person) o;
        if (!errors.hasErrors()) {
            if (!isValidDateFormat((String) errors.getFieldValue("bd"))) {
                errors.rejectValue("bd", "", "Invalid date. Also, the format must be 2022-01-09 (year-month-day)");
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

