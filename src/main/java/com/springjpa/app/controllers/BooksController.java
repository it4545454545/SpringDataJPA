package com.springjpa.app.controllers;

import com.springjpa.app.models.Book;
import com.springjpa.app.models.Person;
import com.springjpa.app.services.BooksService;
import com.springjpa.app.services.PeopleService;
import com.springjpa.app.util.BookSearchTextValidator;
import com.springjpa.app.util.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final PeopleService peopleService;
    private final BookValidator bookValidator;
    private final BookSearchTextValidator bookSearchTextValidator;

    @Autowired
    public BooksController(BooksService bookDAO, BookValidator bookValidator, PeopleService peopleService, BookSearchTextValidator bookSearchTextValidator) {
        this.booksService = bookDAO;
        this.bookValidator = bookValidator;
        this.peopleService = peopleService;
        this.bookSearchTextValidator = bookSearchTextValidator;
    }

    @GetMapping("/favicon.ico")
    public String fav() {
        return "forward:/favicon.ico";
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "page", required = false) Integer currentPageNumber,
                        @RequestParam(value = "perpage", required = false) Integer booksPerPage,
                        @RequestParam(value = "sortbyyear", required = false) Integer sortByYear,
                        @ModelAttribute("searchBook") Book searchBook
    ) {
        int countOfBooks = booksService.countAllBooks();
        List<Integer> pageNumbers;
        int maxPageCount;
        if (booksPerPage != null) {
            maxPageCount = booksService.getPageNumbers(booksPerPage, countOfBooks).size();
        } else {
            maxPageCount = countOfBooks;
        }

        if (currentPageNumber != null) {
            if (currentPageNumber > maxPageCount) {
                if (booksPerPage != null) {
                    model.addAttribute("searchText", "");
                    return "redirect:/books?page=1&perpage=" + booksPerPage;
                }
                model.addAttribute("searchText", "");
                return "redirect:/books?page=1";
            }
        }

        boolean sby_flag = sortByYear != null && sortByYear == 1;

        if ((currentPageNumber != null) && (booksPerPage != null)) {
            if (sby_flag) {
                model.addAttribute("books", booksService.findAll(currentPageNumber, booksPerPage, true));
                model.addAttribute("sortingFlag", true);
            } else {
                model.addAttribute("books", booksService.findAll(currentPageNumber, booksPerPage));
                model.addAttribute("sortingFlag", false);
            }
            pageNumbers = booksService.getPageNumbers(booksPerPage, countOfBooks);
        } else {
            if (booksPerPage == null) booksPerPage = 50;
            if (currentPageNumber == null) currentPageNumber = 1;
            if (sby_flag) {
                model.addAttribute("books", booksService.findAll(true));
                model.addAttribute("sortingFlag", true);
            } else {
                model.addAttribute("books", booksService.findAll());
                model.addAttribute("sortingFlag", false);
            }
            pageNumbers = booksService.getPageNumbers(booksPerPage, countOfBooks);
        }

        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("currentPage", currentPageNumber);
        model.addAttribute("currentBooksPerPage", booksPerPage);

        model.addAttribute("searchText", "");
        model.addAttribute("hideOptions", false);
        model.addAttribute("overdueFormat", booksService.overdueFormat());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        Book book = booksService.findOne(id);
        model.addAttribute("book", book);
        if (book.getPersonOfBook() == null) {
            model.addAttribute("peopleToAssign", peopleService.findAll());
        }
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) return "books/new";
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        booksService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int bookId) {
        booksService.setPersonToBook(bookId, null);
        booksService.releaseTimestamp(bookId);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/assign")
    public String assignPerson(@PathVariable("id") int bookId, @ModelAttribute("person") Person person) {
        //Optimisation via proxyObject
        Person personToAssign = peopleService.getPersonProxy(person.getId());
        booksService.setPersonToBook(bookId, personToAssign);
        booksService.setTimestamp(bookId);
        return "redirect:/books/{id}";
    }

    @PostMapping("/search")
    public String searchBook(@ModelAttribute("searchBook") Book searchBook,
                             BindingResult bindingResult, Model model) {
        bookSearchTextValidator.validate(searchBook, bindingResult);
        if (bindingResult.hasErrors()) {
            return "books/index";
        }
        model.addAttribute("booksFound", booksService.findByTitleIsLikeIgnoreCase(searchBook.getTitle()));
        model.addAttribute("hideOptions", true);
        model.addAttribute("overdueFormat", booksService.overdueFormat());
        return "books/index";
    }
}

