//package com.springmvchiber.app.dao;
//
//import com.springmvchiber.app.models.Person;
//import org.springframework.jdbc.core.RowMapper;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
///**
// * @author Ivan L
// */
//public class PersonMapper implements RowMapper<Person> {
//    @Override
//    public Person mapRow(ResultSet resultSet, int i) throws SQLException {
//       Person person = new Person();
//
//        person.setId(resultSet.getInt("id"));
//        person.setFio(resultSet.getString("fio"));
//        person.setBd(resultSet.getString("birthdate"));
//        return person;
//    }
//}
