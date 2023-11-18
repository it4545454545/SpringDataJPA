package com.springjpa.app.services;

import com.springjpa.app.models.Book;
import com.springjpa.app.models.Person;
import com.springjpa.app.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Ivan L
 */
@Service
// makes all public method transactional(readOnly=true) but @Transactional without readnonly will allow the DB write actions
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository BooksRepository) {
        this.booksRepository = BooksRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    @Transactional
    public void save(Book Book) {
        booksRepository.save(Book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        //save sees the id and updates instead of creating a new one
        booksRepository.save(updatedBook);}

        @Transactional
        public void delete ( int id){
            booksRepository.deleteById(id);
        }

        public List<Book> findByPersonOfBook(Person person){
        return booksRepository.findByPersonOfBook(person);
        }

    @Transactional
    public void updateColumnValue(int bookId, Person newValue) {
        booksRepository.updateColumn(bookId, newValue);
    }

        public void test(){
            System.out.println("f");
        }
    }
