package com.springmvchiber.app.dao;

import com.springmvchiber.app.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Ivan L
 */
@Component
public class BookMapper implements RowMapper<Book> {
    PersonDAO personDAO;

    BookMapper() {
    }



    @Autowired
    BookMapper(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        Book book = new Book();

        book.setId(resultSet.getInt("id"));
        book.setTitle(resultSet.getString("title"));
        book.setIssueDate(resultSet.getString("date"));
        book.setAuthor(resultSet.getString("author"));
        if (personDAO != null){
            int personResult = resultSet.getInt("person_id");
            if (resultSet.wasNull()) book.setPerson_name(null);
            else {book.setPerson_name(personDAO.show(personResult).getFio());}
        }
        return book;
    }
}
