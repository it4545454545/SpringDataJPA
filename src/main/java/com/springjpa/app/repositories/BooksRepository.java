package com.springjpa.app.repositories;


import com.springjpa.app.models.Book;
import com.springjpa.app.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ivan L
 */
@Repository
public interface BooksRepository extends JpaRepository<Book,Integer> {
    List<Book> findByPersonOfBook(Person person);
//    @Modifying
//    @Query("UPDATE Book b SET b.personOfBook = :newValue WHERE b.id = :bookId")
//    void updatePersonOfBook(@Param("bookId") int bookId, @Param("newValue") Person newValue);
}
