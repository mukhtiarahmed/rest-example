package com.hackerrank.assignment.mapper;

import com.hackerrank.assignment.domain.Person;
import com.hackerrank.assignment.dto.PersonDTO;


public class PersonMapper {

    private PersonMapper() {

    }

    public static Person toPerson(PersonDTO dto) {
        Person person = new Person();
        person.setFirstName(dto.getFirstName());
        person.setLastName(dto.getLastName());
        //person.setAge(dto.getAge());
        //  person.setFavouriteColour(dto.getFavouriteColour());
        return person;
    }

    public static void updatePerson(Person oldEntity, Person updated) {
        oldEntity.setFirstName(updated.getFirstName());
        oldEntity.setLastName(updated.getLastName());
        // oldEntity.setAge(updated.getAge());
        oldEntity.setFavouriteColour(updated.getFavouriteColour());
        oldEntity.setIsActive(updated.getIsActive());
    }

}
