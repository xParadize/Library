package com.example.Project2Boot.util;

import com.example.Project2Boot.model.Book;
import com.example.Project2Boot.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {

    private final BooksService booksService;

    @Autowired
    public BookValidator(BooksService booksService) {
        this.booksService = booksService;
    }

    // Для какого класса используется валидация
    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;

        // Валидация на уникальность названия книги (name) (чтобы не вылетала 500 ошибка SQL)
        if (booksService.getBookByName(book.getName()).isPresent()) {
            errors.rejectValue("name", "", "Book with this name already exists"); // <На какое поле пришла ошибка> <опц. код ошибки> <сообщение ошибки>
        }
    }
}
