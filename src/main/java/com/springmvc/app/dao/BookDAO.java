package com.springmvc.app.dao;

import com.springmvc.app.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BookDAO {
    PersonDAO personDAO;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate, PersonDAO personDAO) {
        this.personDAO = personDAO;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BookMapper(personDAO));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id=?", new Object[]{id}, new BookMapper(personDAO)).stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(title,date,author, person_id) VALUES(?,?,?,?)", book.getTitle(), book.getIssueDate(), book.getAuthor(), book.getPerson_name());
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE Book SET date=?, title=?, author=?, person_id=? WHERE id =?", updatedBook.getIssueDate(), updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getPerson_name(), id);

    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id = ?", id);
    }

    public void releaseBook(int bookId){
        jdbcTemplate.update("UPDATE Book SET person_id=null WHERE id =?", bookId);
    }
    public void assignPersonToBook(int bookId, int personId){
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE id=?", personId,bookId);
    }
}
