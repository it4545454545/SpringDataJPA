package com.springmvc.app.dao;

import com.springmvc.app.models.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * @author Ivan L
 */
public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet resultSet, int i) throws SQLException {
       Person person = new Person();

        person.setId(resultSet.getInt("id"));
        person.setFio(resultSet.getString("fio"));
        person.setBd(LocalDate.parse(resultSet.getString("birthdate")));
        return person;
    }
}
