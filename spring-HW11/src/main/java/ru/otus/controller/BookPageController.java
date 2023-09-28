package ru.otus.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/")
public class BookPageController {


    @GetMapping()
    public String mainPage() {
        return "main";
    }
    @GetMapping("/bookpage/{id}")
    public String findBookById(@PathVariable("id") String id, Model model) {
        model.addAttribute("id", id);
        return "bookPage";
    }
    @GetMapping("/add")
    public String getFormForAddBook() {
        return "addBook";
    }

    @GetMapping("/library")
    public String getLibrary() {
        log.info("Get all books in library");
        return "mainPage";
    }
}
