package com.hackerrank.assignment;

import com.hackerrank.assignment.domain.Colour;
import com.hackerrank.assignment.domain.Hobby;
import com.hackerrank.assignment.domain.Person;
import com.hackerrank.assignment.dto.SimplePersonDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestUtils {


    public static Person createPerson() {
        final String firstName = "Ahmed";
        final String lastName = "Mukhtiar";
        return createPerson(firstName, lastName);
    }

    public static Colour createColour(){
        final Colour colour = new Colour();
        colour.setHex("#00FF00");
        colour.setName("Green");
        return colour;
    }

    public static Hobby createHobby(){
        final Hobby hobby = new Hobby();
        hobby.setName("Green");
        return hobby;
    }

    private static Person createPerson(String firstName,  String lastName) {

        final Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setFavouriteColour(createColour());
        person.setDateOfBirth( LocalDate.of(2000, Month.JANUARY, 1));
        person.setHobbies(Arrays.asList(createHobby()));
        return person;
    }

}
