package com.springjpa.app.services;

import com.springjpa.app.models.Book;
import com.springjpa.app.models.Person;
import com.springjpa.app.repositories.BooksRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Ivan L
 */
@Service
// makes all public method transactional(readOnly=true) but @Transactional without readnonly will allow the DB write actions
@Transactional(readOnly = true)
public class BooksService {

    private String overdue;
    private final BooksRepository booksRepository;
    PeopleService peopleService;
    EntityManager entityManager;

    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleService peopleService,EntityManager entityManager) {
        this.booksRepository = booksRepository;
        this.peopleService = peopleService;
        this.entityManager = entityManager;
    }

    public List<Book> findAll() {
        List<Book> bookList = booksRepository.findAll();
        bookList.forEach(book -> book.setOverdue(checkOverdue(book.getId())));
        return bookList;
    }
    public int countAllBooks(){return (int) booksRepository.count();}

    //pagination
    public List<Integer> getPageNumbers(int booksPerPage, int findAllSize) {
        int numberOfPages;
        if( booksPerPage == 0) booksPerPage = 1;

        if(booksPerPage >= findAllSize) { numberOfPages = 1;} else {

            numberOfPages = (int) Math.ceil((double) findAllSize / booksPerPage);}
        List<Integer> listOfPages = IntStream.rangeClosed(1, numberOfPages)
                .boxed()
                .collect(Collectors.toUnmodifiableList());

        return listOfPages;
    }

    public List<Book> findAll(int pageNumber, int booksPerPage) {
        if (pageNumber > 0) --pageNumber;
        List<Book> bookList = booksRepository.findAll(PageRequest.of(pageNumber, booksPerPage)).getContent();
        bookList.forEach(book -> book.setOverdue(checkOverdue(book.getId())));
        return bookList;
    }

    //sorting
    public List<Book> findAll(boolean sortByYear) {
        List<Book> bookList = booksRepository.findAll(Sort.by("issueDate"));
        bookList.forEach(book -> book.setOverdue(checkOverdue(book.getId())));
        return bookList;
    }

    //pagination + sorting
    public List<Book> findAll(int pageNumber, int booksPerPage, boolean sortByYear) {
        List<Book> bookList;
        if (pageNumber > 0) --pageNumber;
        if (sortByYear) {
            bookList = booksRepository.findAll(PageRequest.of(pageNumber, booksPerPage, Sort.by("issueDate"))).getContent();
            bookList.forEach(book -> book.setOverdue(checkOverdue(book.getId())));
            return bookList;
        } else {
            bookList = booksRepository.findAll(PageRequest.of(pageNumber, booksPerPage)).getContent();
            bookList.forEach(book -> book.setOverdue(checkOverdue(book.getId())));
            return bookList;
        }
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
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    public List<Book> findByPersonOfBook(Person person) {
        return booksRepository.findByPersonOfBook(person);
    }

    @Transactional
    public void setPersonToBook(int bookId, Person newPerson) {
        Optional<Book> book = getBookProxy(bookId);
       book.ifPresent(value -> value.setPersonOfBook(newPerson));
    }


    public Optional<Book> getBookProxy(int bookId) {
        try (Session session = entityManager.unwrap(Session.class);
        ) {
            return Optional.of(session.load(Book.class, bookId));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
    public List<Book> findByTitleIsLikeIgnoreCase(String title){
        List<Book> bookList = booksRepository.findByTitleIsLikeIgnoreCase("%" + title + "%");;
        bookList.forEach(book -> book.setOverdue(checkOverdue(book.getId())));
        return bookList;
    }
    @Transactional
    public void setTimestamp(int bookId){
        findOne(bookId).setTimestamp(new Timestamp(System.currentTimeMillis()));
    }

    @Transactional
    public void releaseTimestamp(int bookId){
        findOne(bookId).setTimestamp(Timestamp.valueOf("9999-12-31 00:00:00.000000"));
    }

    public boolean checkOverdue(int bookId){
        Timestamp bookTimestamp = findOne(bookId).getTimestamp();
        Timestamp tenDaysLater = new Timestamp(bookTimestamp.getTime() + (10 * 24 * 60 * 60 * 1000));
        if ( new Timestamp(System.currentTimeMillis()).after(tenDaysLater)) return true;
        return false;
    }
    public DateTimeFormatter overdueFormat(){
        return DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public void test() {
        System.out.println(booksRepository.count());
        booksRepository.count();
    }
}

