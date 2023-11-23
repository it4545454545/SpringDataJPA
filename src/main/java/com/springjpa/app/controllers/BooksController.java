package com.springjpa.app.controllers;

import com.springjpa.app.models.Book;
import com.springjpa.app.models.Person;
import com.springjpa.app.services.BooksService;
import com.springjpa.app.services.PeopleService;
import com.springjpa.app.util.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final PeopleService peopleService;
    private final BookValidator bookValidator;

    @Autowired
    public BooksController(BooksService bookDAO, BookValidator bookValidator, PeopleService peopleService) {
        this.booksService = bookDAO;
        this.bookValidator = bookValidator;
        this.peopleService = peopleService;
    }

    @GetMapping("/favicon.ico")
    public String fav() {
        return "forward:/favicon.ico";
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "page", required = false) Integer currentPageNumber,
                        @RequestParam(value = "perpage", required = false) Integer booksPerPage,
                        @RequestParam(value = "sortbyyear", required = false) Integer sortByYear
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
                    return "redirect:/books?page=1&perpage=" + booksPerPage;
                }
                return "redirect:/books?page=1";
            }
        }

        boolean sby_flag = sortByYear != null && sortByYear == 1;

        if ((currentPageNumber != null) && (booksPerPage != null)) {
            if (sby_flag) {
                model.addAttribute("books", booksService.findAll(currentPageNumber, booksPerPage, true));
            } else {
                model.addAttribute("books", booksService.findAll(currentPageNumber, booksPerPage));
            }
            pageNumbers = booksService.getPageNumbers(booksPerPage, countOfBooks);
        } else {
            if (booksPerPage == null) booksPerPage = 50;
            if (currentPageNumber == null) currentPageNumber = 1;
            if (sby_flag) {
                model.addAttribute("books", booksService.findAll(true));
            } else model.addAttribute("books", booksService.findAll());
            pageNumbers = booksService.getPageNumbers(booksPerPage, countOfBooks);
        }

        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("currentPage", currentPageNumber);
        model.addAttribute("currentBooksPerPage", booksPerPage);

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
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/assign")
    public String assignPerson(@PathVariable("id") int bookId, @ModelAttribute("person") Person person) {
        //Optimisation via proxyObject
        Person personToAssign = peopleService.getPersonProxy(person.getId());
        booksService.setPersonToBook(bookId, personToAssign);
        return "redirect:/books/{id}";
    }
}

