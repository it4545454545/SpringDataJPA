package com.springmvc.app.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class Person {
    private int id;
    @NotEmpty(message = "ALO NE PUSTOE")
    @Size(min=2,max=30, message = "ot 2 do 30 bukv")
    private String fio;
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private LocalDate bd;
    public Person() {}
    public Person(int id, String name, LocalDate bd) {
        this.id = id;
        this.fio = name;
        this.bd = bd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public     @DateTimeFormat(pattern = "yyyy.MM.dd") LocalDate getBd() {
        return bd;
    }

    public void setBd(LocalDate bd) {
        this.bd = bd;
    }
}