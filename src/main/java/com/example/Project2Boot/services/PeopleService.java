package com.example.Project2Boot.services;

import com.example.Project2Boot.model.Book;
import com.example.Project2Boot.model.Person;
import com.example.Project2Boot.repositories.BooksRepository;
import com.example.Project2Boot.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, BooksRepository booksRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.booksRepository = booksRepository;
        this.passwordEncoder = passwordEncoder;
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

    // Регистрация в системе (для админов)
    @Transactional
    public void register(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_CUSTOMER");
        peopleRepository.save(person);
    }

    // Создание человека (сервис)
    @Transactional
    public void createPerson(Person createdPerson) {
        createdPerson.setRole("ROLE_CUSTOMER");
        peopleRepository.save(createdPerson);
    }

    // Изменение данных о человеке (сервис)
    @Transactional
    public void updatePerson(int id, Person updatedPerson) {
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
