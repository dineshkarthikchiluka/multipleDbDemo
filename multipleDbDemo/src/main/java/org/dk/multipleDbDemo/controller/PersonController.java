package org.dk.multipleDbDemo.controller;

import org.dk.multipleDbDemo.authordb.Author;
import org.dk.multipleDbDemo.persondb.Person;
import org.dk.multipleDbDemo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/addPerson")
    public Person addPerson(@RequestBody Person person){
        return personService.addPerson(person);
    }
}
