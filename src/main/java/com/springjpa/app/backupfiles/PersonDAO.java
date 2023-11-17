//package com.springjpa.app.dao;
//
//import com.springjpa.app.models.Book;
//import com.springjpa.app.models.Person;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Component
//public class PersonDAO {
//    SessionFactory sessionFactory;
//
//    @Autowired
//    public PersonDAO(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }
//
//    @Transactional
//    public List<Person> index() {
//        Session session = sessionFactory.getCurrentSession();
//        return session.createQuery("select p from Person p", Person.class).getResultList();
//    }
//    @Transactional
//    public Person show(int id) {
//        Session session = sessionFactory.getCurrentSession();
//        return session.get(Person.class, id);
//    }
//    @Transactional
//    public void save(Person person) {
//        Session session = sessionFactory.getCurrentSession();
//        session.save(person);
//    }
//    @Transactional
//    public void update(int id, Person updatedPerson) {
//        Session session = sessionFactory.getCurrentSession();
//        Person person = session.get(Person.class, id);
//        person.setBd(updatedPerson.getBd());
//        person.setFio(updatedPerson.getFio());
//    }
//    @Transactional
//    public void delete(int id) {
//        Session session = sessionFactory.getCurrentSession();
//        session.remove(session.get(Person.class,id));
//    }
//    @Transactional
//    public List<Book> showPersonsBooks(int id) {
//        Session session = sessionFactory.getCurrentSession();
//        return session.createQuery("select b from Book b where b.personOfBook.id = :id", Book.class)
//                .setParameter("id", id)
//                .getResultList();
//    }
//
//}
