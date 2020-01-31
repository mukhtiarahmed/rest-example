package com.hackerrank.assignment.service;

import com.hackerrank.assignment.TestUtils;
import com.hackerrank.assignment.config.TestAuditingConfiguration;
import com.hackerrank.assignment.domain.Colour;
import com.hackerrank.assignment.domain.Hobby;
import com.hackerrank.assignment.domain.Person;
import com.hackerrank.assignment.exception.AssignmentException;
import com.hackerrank.assignment.repository.ColourRepository;
import com.hackerrank.assignment.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * The Person Service Test class.
 *
 * @author mukhtiar.ahmed
 */
@Slf4j
@RunWith(SpringRunner.class)
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@Sql({"classpath:import.sql"})
@SpringBootTest
public class PersonServiceTest {


    @Autowired
    private ColourRepository colourRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    private Person person;

    @Before
    public void setup() throws AssignmentException {
        MockitoAnnotations.initMocks(this);
        Colour colour =  colourRepository.save(TestUtils.createColour());
        Person person = TestUtils.createPerson();
        List<Hobby> hobbies = new ArrayList<>();
        hobbies.add(TestUtils.createHobby());
        person.setHobbies(hobbies);
        person.setFavouriteColour(colour);
        this.person = personRepository.save(person);
    }

    @Test
    public void testUpdatingAPerson() throws  Exception {
        LocalDate localDate = LocalDate.now();
        person.setDateOfBirth(localDate);
        person.setFirstName("Muhammad");
        person.setLastName("Saleem");
        Person persist = personService.update(person.getId(), person);
        assertThat(persist, is(notNullValue()));
        assertThat(persist.getId(), is(notNullValue()));
        assertThat(persist.getFirstName(), is(person.getFirstName()));
        assertThat(persist.getLastName(), is(person.getLastName()));
        assertThat(persist.getFavouriteColour(), is(person.getFavouriteColour()));
        assertThat(persist.getDateOfBirth(), is(localDate));
        assertThat(persist.getHobbies(), is(person.getHobbies()));
        assertThat(persist.getLastModifiedOn(), is(notNullValue()));
        assertThat(persist.getCreatedOn(), is(notNullValue()));
        assertThat(persist.getLastModifiedBy(), is(notNullValue()));
        assertThat(persist.getCreatedBy(), is(notNullValue()));
    }

}
