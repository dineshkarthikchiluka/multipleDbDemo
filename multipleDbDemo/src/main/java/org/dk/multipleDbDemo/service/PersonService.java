package org.dk.multipleDbDemo.service;

import org.dk.multipleDbDemo.authordb.Author;
import org.dk.multipleDbDemo.persondb.Person;
import org.dk.multipleDbDemo.persondb.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public Person addPerson(Person person) {
        personRepository.save(person);
        return person;
    }
}
