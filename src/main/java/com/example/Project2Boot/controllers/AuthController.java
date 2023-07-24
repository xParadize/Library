package com.example.Project2Boot.controllers;

import com.example.Project2Boot.model.Person;
import com.example.Project2Boot.services.PeopleService;
import com.example.Project2Boot.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final PersonValidator personValidator;
    private final PeopleService peopleService;

    @Autowired
    public AuthController(PersonValidator personValidator, PeopleService peopleService) {
        this.personValidator = personValidator;
        this.peopleService = peopleService;
    }

    // Вход
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    // Регистрация
    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person) {
        return "auth/registration";
    }

    @PostMapping("registration")
    public String performRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) return "/auth/registration";

        peopleService.register(person);

        return "redirect:/login";
    }

    @GetMapping("/redirection")
    public String redirection() {
        return "auth/redirection";
    }

    // TODO: inject profile system
//    @GetMapping("/profile")
//    public String profile() {
//        return "auth/profile";
//    }
}
