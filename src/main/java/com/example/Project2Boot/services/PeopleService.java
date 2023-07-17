package com.example.Project2Boot.services;

import com.example.Project2Boot.model.Book;
import com.example.Project2Boot.model.Person;
import com.example.Project2Boot.repositories.BooksRepository;
import com.example.Project2Boot.repositories.PeopleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Validated
public class PeopleService {

    private final PeopleRepository peopleRepository;
    private final BooksRepository booksRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, BooksRepository booksRepository) {
        this.peopleRepository = peopleRepository;
        this.booksRepository = booksRepository;
    }

    // Показ всех людей на /people
    public List<Person> showAllPeople() {
        return peopleRepository.findAll();
    }

    // Показ информации о человеке (сервис)
    public Person showOnePerson(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        return person.orElse(null);
    }

    // Создание человека (сервис)
    @Transactional
    public void createPerson(@Valid Person createdPerson) {
        peopleRepository.save(createdPerson);
    }

    // Изменение данных о человеке (сервис)
    @Transactional
    public void updatePerson(int id, @Valid Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    // Удаление человека (сервис)
    @Transactional
    public void deletePerson(int id) {
        peopleRepository.deleteById(id);
    }

    // Показывает все книги, которые есть у человека на данный момент (сервис)
    public List<Book> availableBooks(int id) {
        return booksRepository.findBooksByOwnerId(id);
    }
}
