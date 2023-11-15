package com.springmvchiber.app.dao;

import com.springmvchiber.app.models.Book;
import com.springmvchiber.app.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class PersonDAO {
    SessionFactory sessionFactory;
    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory  = sessionFactory;
    }
    @Transactional
    public List<Person> index() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select p from Person p", Person.class).getResultList();
    }

    public Person show(int id) {
        return null;
    }

    public void save(Person person) {

    }

    public void update(int id, Person updatedPerson) {


    }
    public void delete(int id) {
    }

    public List<Book> showPersonsBooks(int id) {
        return null;
    }

}
