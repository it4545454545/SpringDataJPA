package com.springmvc.app.controllers;

import com.springmvc.app.dao.BookDAO;
import com.springmvc.app.dao.PersonDAO;
import com.springmvc.app.models.Book;
import com.springmvc.app.models.Person;
import com.springmvc.app.util.BookValidator;
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
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person")Person person) {
        Book book = bookDAO.show(id);
        model.addAttribute("book", book);
        if (book.getPerson_name().equals("Kniga ne prinadlejit nikomy")){
            model.addAttribute("emptyPerson", person);
            model.addAttribute("peopleToAssign", personDAO.index());
            model.addAttribute("assignText", "Viberite person to assign");
        }
        return "books/show";
    }
    @GetMapping("/new")
    public String newbook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book")@Valid Book book, BindingResult bindingResult) {
//        bookValidator.validate(book, bindingResult);
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

