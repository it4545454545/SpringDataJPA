package com.springmvchiber.app.controllers;

import com.springmvchiber.app.dao.BookDAO;
import com.springmvchiber.app.dao.PersonDAO;
import com.springmvchiber.app.models.Book;
import com.springmvchiber.app.models.Person;
import com.springmvchiber.app.util.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;
    private final BookValidator bookValidator;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, BookValidator bookValidator, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
        this.personDAO = personDAO;
    }
    @GetMapping("/favicon.ico")
        public String fav(){return "forward:/favicon.ico";}
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        Book book = bookDAO.show(id);
        model.addAttribute("book", book);
        if (book.getPersonOfBook() == null){
            model.addAttribute("peopleToAssign", personDAO.index());
        }
        return "books/show";
    }
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book")@Valid Book book, BindingResult bindingResult) {
      bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors()) return "books/new";
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        bookValidator.validate(book, bindingResult);

        if(bindingResult.hasErrors()){return "books/edit";}
        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int bookId){
        bookDAO.releaseBook(bookId);
        return "redirect:/books/{id}";
    }
    @PatchMapping("/{id}/assign")
    public String assignPerson(@PathVariable("id") int bookId, @ModelAttribute("person") Person person){
        bookDAO.assignPersonToBook(bookId,person.getId());
        return "redirect:/books/{id}";
    }
}

