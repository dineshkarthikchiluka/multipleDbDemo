package org.dk.multipleDbDemo.controller;

import org.dk.multipleDbDemo.authordb.Author;
import org.dk.multipleDbDemo.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @PostMapping("/addAuthor")
    public Author addAuthor(@RequestBody Author author){
        return authorService.addAuthor(author);
    }
}
