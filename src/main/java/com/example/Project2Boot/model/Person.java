package com.example.Project2Boot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Entity
@Table(name = "Person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "This field should not be empty")
    @Pattern(regexp = "^[\\p{L} .'-]+$", message = "Only alphabet symbols are allowed")
    @Size(min = 3, message = "Name can't be less than 3 symbols")
    @Column(name = "name")
    private String name;

    @NotNull(message = "This field should not be empty")
    @Range(min = 6, max = 122, message = "Person's age can not be less than 6 and more than 122 years")
    @Digits(integer = 3, fraction = 0, message = "Only numbers are allowed")
    @Column(name = "age")
    private Integer age;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person() {}

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
