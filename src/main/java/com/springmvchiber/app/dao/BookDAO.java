package com.springmvchiber.app.dao;

import com.springmvchiber.app.models.Book;
import com.springmvchiber.app.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class BookDAO {
    SessionFactory sessionFactory;
    public BookDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<Book> index() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select b from Book b", Book.class).getResultList();
    }

    @Transactional public Book show(int id) {
        Session session = sessionFactory.getCurrentSession();
       return session.get(Book.class,id);
    }

    @Transactional public void save(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
    }

    @Transactional public void update(int id, Book updatedBook) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);

        book.setAuthor(updatedBook.getAuthor());
        book.setTitle(updatedBook.getTitle());
        book.setIssueDate(updatedBook.getIssueDate());
    }

    @Transactional public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Book.class,id));
    }
    @Transactional public void releaseBook(int bookId){
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, bookId);
       Person person = (Person) session.merge(book.getPersonOfBook());
       person.getBooksOfPerson().remove(book);
        book.setPersonOfBook(null);
    }
    @Transactional public void assignPersonToBook(int bookId, int personId){
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class,bookId);
        Person person = session.get(Person.class,personId);
        book.setPersonOfBook(person);
    }

    @Transactional public Person getPersonOfBook(int bookId){
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, session.get(Book.class, bookId).getId());
    }


}
