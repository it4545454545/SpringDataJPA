package com.springjpa.app.services;

import com.springjpa.app.models.Person;
import com.springjpa.app.repositories.PeopleRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * @author Ivan L
 */
@Service
// makes all public method transactional(readOnly=true) but @Transactional without readnonly will allow the DB write actions
@Transactional(readOnly = true)
public class PeopleService {
    EntityManager entityManager;
    private final PeopleRepository peopleRepository;


    @Autowired
    public PeopleService(EntityManager entityManager, PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
        this.entityManager = entityManager;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        //save sees the id and updates instead of creating a new one
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    @Transactional
    public Person getPersonProxy(int personId) {
        try (Session session = entityManager.unwrap(Session.class);
        ) {
            return session.load(Person.class, personId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
