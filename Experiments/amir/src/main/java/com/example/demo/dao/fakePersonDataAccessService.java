package com.example.demo.dao;

import com.example.demo.model.person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class fakePersonDataAccessService implements personDao {
    private static List<person> DB = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, person person) {
        DB.add(new person(id, person.getName()));
        return 1;
    }

    @Override
    public List<person> selectAllPeople() {
        return DB;
    }

    @Override
    public Optional<person> selectPersonByID(UUID id) {
        return DB.stream().filter(person -> person.getId().equals(id)).findFirst();
    }

    @Override
    public int deletePersonByID(UUID id) {
        Optional<person> personMaybe = selectPersonByID(id);
        if(personMaybe.isEmpty()){
            return 0;
        }
        DB.remove(personMaybe.get());
        return 1;
    }

    @Override
    public int updatePersonByID(UUID id, person updatePerson) {
        return selectPersonByID(id).map(p -> {
            int indexOfPersonToUpdate= DB.indexOf(p);
            if(indexOfPersonToUpdate >= 0){
                DB.set(indexOfPersonToUpdate, new person(id, updatePerson.getName()));
                return 1;
            }
            return 0;
        }).orElse(0);
    }
}