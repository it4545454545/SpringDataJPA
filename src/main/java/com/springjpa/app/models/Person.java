package com.springjpa.app.models;

import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "fio")
    @NotEmpty(message = "Can not be empty, Min 2 - Max 50 symbols")
    @Size(min=2,max=50, message = "Min 2 - Max 50 symbols")
    private String fio;
    @ColumnTransformer(read = "to_char(birthdate, 'YYYY-MM-DD')", write = "to_date(?,'YYYY-MM-DD')")
    @Column(name = "birthdate")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "The format must be 2022-09-29")
    private String bd;

    @OneToMany(mappedBy = "personOfBook")
    List<Book> booksOfPerson;
    public Person() {}
    public Person(int id, String name, String bd) {
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

    public String getBd() {
        return bd;
    }

    public void setBd(String bd) {
        this.bd = bd;
    }

    public List<Book> getBooksOfPerson() {
        return booksOfPerson;
    }

    public void setBooksOfPerson(List<Book> booksOfPerson) {
        this.booksOfPerson = booksOfPerson;
    }
}