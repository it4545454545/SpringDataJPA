package com.springmvc.app.dao;

import com.springmvc.app.models.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * @author Ivan L
 */
public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        Book book = new Book();

        book.setId(resultSet.getInt("id"));
        book.setTitle(resultSet.getString("title"));
        book.setIssueDate(LocalDate.parse(resultSet.getString("date")));
        book.setAuthor(resultSet.getString("author"));
        book.setPerson_id(resultSet.getInt("person_id"));
        return book;
    }
}
