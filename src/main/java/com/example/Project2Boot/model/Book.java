package com.example.Project2Boot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@Entity
@Table(name = "Book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "This field should not be empty")
    @Pattern(regexp = "^[\\p{L} .'-]+$", message = "Only alphabet symbols are allowed")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "This field should not be empty")
    @Pattern(regexp = "^[\\p{L} .'-]+$", message = "Only alphabet symbols are allowed")
    @Size(min = 1, message = "Author's name can't be less than 3 symbols")
    @Column(name = "author")
    private String author;

    @NotNull(message = "This field should not be empty")
    @Range(min = 703, max = 2023, message = "Date of creation can not be less than 703 and more than 2023 years")
    @Digits(integer = 4, fraction = 0, message = "Only numbers are allowed")
    @Column(name = "date_of_creation")
    private Integer dateOfCreation;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @Column(name = "taken_at")
    private LocalDate takenAt;

    public Book() {}

    public Book(String name, String author, int dateOfCreation) {
        this.name = name;
        this.author = author;
        this.dateOfCreation = dateOfCreation;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Integer dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public LocalDate getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(LocalDate takenAt) {
        this.takenAt = takenAt;
    }
}
