package com.example.demo.service;

import com.example.demo.dao.personDao;
import com.example.demo.model.person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class personService {

    private final personDao persondao;

    @Autowired
    public personService(@Qualifier("fakeDao") personDao persondao) {
        this.persondao = persondao;
    }

    public int addPerson(person person){
        return persondao.insertPerson(person);
    }

    public List<person> getAllPeople(){
        return persondao.selectAllPeople();
    }

    public Optional<person> getPersonById(UUID id){
        return persondao.selectPersonByID(id);
    }

    public int deletePerson(UUID id){
        return persondao.deletePersonByID(id);
    }

    public int updatePerson(UUID id, person person){
        return persondao.updatePersonByID(id, person);
    }
}

