package com.springmvchiber.app.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class Book {
    private int id;
    private String person_name;

    @NotEmpty
    private String title;
    @NotEmpty
    private String author;
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "The format must be 2022-09-29")
    private String issueDate;

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

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

}
