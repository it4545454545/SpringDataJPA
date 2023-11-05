package com.springmvc.app.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

public class Book {
    private int id;
    private String person_name;

    @NotEmpty
    private String title;
    @NotEmpty
    private String author;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate issueDate;

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPerson_name() {
        return this.person_name;
    }

    public void setPerson_name(String person_idw) {
        this.person_name = person_idw;
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

    public @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

}
