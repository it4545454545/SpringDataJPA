package com.springmvc.app.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

public class Book {
    private int id;
    private int person_id;

    public Book(int id, int person_id, String title, String author, LocalDate issueDate) {
        this.id = id;
        this.person_id = person_id;
        this.title = title;
        this.author = author;
        this.issueDate = issueDate;
    }

    @NotEmpty
    private String title;
    @NotEmpty
    private String author;
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private LocalDate issueDate;

    public Book(){}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public @DateTimeFormat(pattern = "yyyy.MM.dd") LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }


}
