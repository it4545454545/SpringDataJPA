package com.springjpa.app.repositories;


import com.springjpa.app.models.Book;
import com.springjpa.app.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ivan L
 */
@Repository
public interface BooksRepository extends JpaRepository<Book,Integer> {
    List<Book> findByPersonOfBook(Person person);
}
