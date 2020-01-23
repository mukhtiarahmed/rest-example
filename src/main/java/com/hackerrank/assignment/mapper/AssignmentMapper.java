package com.hackerrank.assignment.mapper;


import com.hackerrank.assignment.domain.Colour;
import com.hackerrank.assignment.domain.Hobby;
import com.hackerrank.assignment.domain.Person;
import com.hackerrank.assignment.dto.ColourDTO;
import com.hackerrank.assignment.dto.HobbyDTO;
import com.hackerrank.assignment.dto.PersonDTO;
import com.hackerrank.assignment.dto.SimplePersonDTO;
import com.hackerrank.assignment.util.AssignmentHelper;

import java.util.stream.Collectors;

/**
 * The Mapper class
 *
 * @author mukhtiar.ahmed
 * @version 1.0
 */
public class AssignmentMapper {

    private AssignmentMapper() {

    }

    public static SimplePersonDTO toSimplePersonDTO(Person entity) {
        if (entity == null) return null;

        SimplePersonDTO dto = new SimplePersonDTO();
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setAge(AssignmentHelper.calculateAge(entity.getDateOfBirth()));
        dto.setFavouriteColour(entity.getFavouriteColour().getName());
        dto.setHobby(entity.getHobbies().stream().map(Hobby::getName).collect(Collectors.toList()));
        return dto;

    }

    public static PersonDTO toPersonDTO(Person entity) {
        if (entity == null) return null;
        PersonDTO dto = new PersonDTO();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setAge(AssignmentHelper.calculateAge(entity.getDateOfBirth()));
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setColourId(entity.getFavouriteColour().getId());
        dto.setHobbies(entity.getHobbies().stream().map(Hobby::getId).collect(Collectors.toList()));
        return dto;

    }

    public static HobbyDTO toHobbyDTO(Hobby hobby) {
        if (hobby == null) return null;

        HobbyDTO dto = new HobbyDTO();
        dto.setId(hobby.getId());
        dto.setName(hobby.getName());
        return dto;
    }

    public static ColourDTO toColourDTO(Colour colour) {
        if (colour == null) return null;

        ColourDTO dto = new ColourDTO();
        dto.setId(colour.getId());
        dto.setName(colour.getName());
        dto.setHex(colour.getHex());
        return dto;
    }

}
