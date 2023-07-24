package com.example.Project2Boot.controllers;

import com.example.Project2Boot.model.Book;
import com.example.Project2Boot.model.Person;
import com.example.Project2Boot.services.BooksService;
import com.example.Project2Boot.services.PeopleService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {

    // Внедрение сервисов через конструктор
    private final PeopleService peopleService;
    private final BooksService booksService;

    @Autowired
    public PeopleController(PeopleService peopleService, BooksService booksService) {
        this.peopleService = peopleService;
        this.booksService = booksService;
    }

    // Главная страница с показом всех людей
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", peopleService.showAllPeople());
        return "people/index";
    }

    // Страница с просмотром информации о человеке + список книг, которые он взял (также присутствует проверка книг на просроченность)
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,
                       @ModelAttribute("book") Book book) {

        model.addAttribute("person", peopleService.showOnePerson(id));
        model.addAttribute("books", peopleService.availableBooks(id));

        // Книга считается просроченной, если человек не вернул ее в течение 10 дней после того, как взял
        model.addAttribute("isBookOutdated", booksService.isBookOutdated(book.getId()));

        return "people/show";
    }

    // Страница добавления нового человека в БД
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "people/new";
        peopleService.createPerson(person);
        return "redirect:/people";
    }

    // Страница редактирования информации о человеке
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.showOnePerson(id));
        return "people/edit";
    }

    // Метод для кнопки "Изменить данные о человеке"
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "people/edit";

        peopleService.updatePerson(id, person);
        return "redirect:/people";
    }

    // Метод для кнопки "Удалить человека"
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.deletePerson(id);
        return "redirect:/people";
    }
}