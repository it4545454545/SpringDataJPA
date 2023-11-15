package com.springmvchiber.app.dao;

import com.springmvchiber.app.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.List;

@Component
public class BookDAO {
    PersonDAO personDAO;


    @Autowired
    public BookDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;

    }

    public List<Book> index() {
        return null;
    }

    public Book show(int id) {
        return null;
    }

    public void save(Book book) {

    }

    public void update(int id, Book updatedBook) {
    }

    public void delete(int id) {

    }
    public void releaseBook(int bookId){

    }
    public void assignPersonToBook(int bookId, int personId){

    }
}
