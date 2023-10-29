package com.springmvc.app.util;

import com.springmvc.app.dao.PersonDAO;
import com.springmvc.app.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Ivan L
 */
@Component
public class PersonValidator implements Validator {
private final PersonDAO personDAO;
@Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        //est li takoi person v bd c email
//        if (personDAO.show(person.getEmail()).isPresent()){
//            errors.rejectValue("email","","this email is already taken");
        }

    }

