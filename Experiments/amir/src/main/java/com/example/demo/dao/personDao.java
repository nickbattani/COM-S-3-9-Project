package com.example.demo.dao;

import com.example.demo.model.person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface personDao {
    int insertPerson (UUID id, person person);

    default int insertPerson(person person){
        UUID id = UUID.randomUUID();
        return insertPerson(id, person);
    }

    List<person> selectAllPeople();

    Optional<person> selectPersonByID(UUID id);

    int deletePersonByID(UUID id);

    int updatePersonByID(UUID id, person person);


}
