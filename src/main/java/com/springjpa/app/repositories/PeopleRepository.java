package com.springjpa.app.repositories;


import com.springjpa.app.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ivan L
 */
@Repository
public interface PeopleRepository extends JpaRepository<Person,Integer> {

}
