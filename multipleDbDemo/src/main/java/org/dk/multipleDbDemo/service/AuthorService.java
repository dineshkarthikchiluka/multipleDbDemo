package org.dk.multipleDbDemo.service;

import org.dk.multipleDbDemo.authordb.Author;
import org.dk.multipleDbDemo.authordb.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    public Author addAuthor(Author author) {
        authorRepository.save(author);
        return author;
    }
}
