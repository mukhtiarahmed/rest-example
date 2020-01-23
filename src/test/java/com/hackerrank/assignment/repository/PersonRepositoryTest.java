package com.hackerrank.assignment.repository;

import com.hackerrank.assignment.config.TestAuditingConfiguration;
import com.hackerrank.assignment.domain.Colour;
import com.hackerrank.assignment.domain.Hobby;
import com.hackerrank.assignment.domain.Person;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.core.IsNot;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

/**
 * The Helper class.
 *
 * @author mukhtiar.ahmed
 */
@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
@Import(TestAuditingConfiguration.class)
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@Sql({"classpath:import.sql"})
public class PersonRepositoryTest {

    @Autowired
    private AuditorAware<String> auditorAware;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Colour colour;

    private  List<Colour> colours;

    private List<Hobby> hobbies;

    private final LocalDate dateOfBirth = LocalDate.of(2000, Month.JANUARY, 1);


    @Before
    public void setup() {
        colours = entityManager.getEntityManager().createQuery(
                "SELECT c FROM  colour c", Colour.class).getResultList();

        colour = colours.get(0);
        hobbies = entityManager.getEntityManager().createQuery(
                "SELECT h FROM  hobby h", Hobby.class).getResultList();


    }

    @Test
    public void testCurrentAuditor() {
        String currentAuditor = auditorAware.getCurrentAuditor().get();
        assertEquals("Test  Auditor", currentAuditor);
    }

    @Test
    public void testCreatingAPerson() throws Exception {

        final Person person =  createPerson();
        final Person persist = personRepository.save(person);
        assertThat(persist, is(notNullValue()));
        assertThat(persist.getId(), is(notNullValue()));
        assertThat(persist.getFirstName(), is(person.getFirstName()));
        assertThat(persist.getLastName(), is(person.getLastName()));
        assertThat(persist.getFavouriteColour(), is(colour));
        assertThat(persist.getDateOfBirth(), is(dateOfBirth));
        assertThat(persist.getHobbies(), is(person.getHobbies()));
        assertThat(persist.getLastModifiedOn(), is(notNullValue()));
        assertThat(persist.getCreatedOn(), is(notNullValue()));
        assertThat(persist.getLastModifiedBy(), is(notNullValue()));
        assertThat(persist.getCreatedBy(), is(notNullValue()));

        assertThat(persist.getIsActive(), is(Boolean.TRUE));
        assertThat(personRepository.count(), is(1L));
        final Optional<Person> optional = personRepository.findById(persist.getId());
        assertThat(optional.isPresent(), is(Boolean.TRUE));
        assertThat(optional, is(Optional.of(person)));
        final Person found = optional.get();
        assertThat(found, is(notNullValue()));
        assertThat(found.getId(), is(notNullValue()));
        assertThat(found.getFirstName(), is(person.getFirstName()));
        assertThat(found.getLastName(), is(person.getLastName()));
        assertThat(found.getFavouriteColour(), is(colour));
        assertThat(found.getIsActive(), is(Boolean.TRUE));
        assertThat(found.getDateOfBirth(), is(dateOfBirth));
        assertThat(found.getHobbies(), is(person.getHobbies()));
        assertThat(found.getCreatedOn(), is(persist.getCreatedOn()));
        assertThat(found.getLastModifiedOn(), is(persist.getLastModifiedOn()));
        assertThat(found.getCreatedBy(),  is(persist.getCreatedBy()));
        assertThat(found.getLastModifiedBy(),  is(persist.getLastModifiedBy()));

    }

    @Test
    public void testUpdatingAPerson() throws Exception {
        final Person person =  createPerson();
        final Person persist  = entityManager.persist(person);
        final Person old = entityManager.find(Person.class, persist.getId());

        final String firstName = "Muhammad";
        final String lastName = "Ali";
        final LocalDate dateOfBirth = LocalDate.of(2011, Month.DECEMBER, 12);
        final  Colour colour = colours.get(1);
        final List<Hobby> hobbies = this.hobbies.subList(5,10);
        old.setFirstName(firstName);
        old.setLastName(lastName);
        old.setHobbies(hobbies);
        old.setFavouriteColour(colour);
        old.setIsActive(Boolean.FALSE);
        old.setDateOfBirth(dateOfBirth);


        final Person updatePerson = personRepository.save(old);
        assertThat(updatePerson, is(notNullValue()));
        assertThat(updatePerson.getId(), is(old.getId()));
        assertThat(updatePerson.getFirstName(), is(firstName));
        assertThat(updatePerson.getLastName(), is(lastName));
        assertThat(updatePerson.getFavouriteColour(), is(colour));
        assertThat(updatePerson.getIsActive(), is(Boolean.FALSE));
        assertThat(updatePerson.getDateOfBirth(), is(dateOfBirth));
        assertThat(updatePerson.getHobbies(), is(hobbies));
        assertThat(updatePerson.getCreatedBy(),  is(old.getCreatedBy()));
        assertThat(updatePerson.getLastModifiedBy(),  is(old.getLastModifiedBy()));
        assertThat(updatePerson.getCreatedOn(), is(old.getCreatedOn()));

    }

    @Test
    public void testFindAll() throws Exception {
        final Person person1 = createPerson("Mukhtiar", "Ahmed");
        final Person person2 = createPerson("Syed", "Ali");
        final Person person3 = createPerson("Muhammad", "Saleem");
        entityManager.persist(person1);
        entityManager.persist(person2);
        entityManager.persist(person3);
        List<Person> list =  personRepository.findAll();
        assertThat( list, hasItem(hasProperty("firstName", is("Syed"))));
        assertThat( list, hasItem(hasProperty("firstName", is("Mukhtiar"))));
        assertThat( list, hasItem(hasProperty("firstName", is("Muhammad"))));
        assertThat(list.size(), is(3));

    }

    @Test
    public void testFindById() throws Exception {
        final Person person =  createPerson();
        final Person persist  = entityManager.persist(person);
        final Optional<Person> optional = personRepository.findById(persist.getId());
        assertThat(optional.isPresent(), is(Boolean.TRUE));
        assertThat(optional, is(Optional.of(person)));
        final Person found = optional.get();
        assertThat(found, is(notNullValue()));
        assertThat(found.getId(), is(notNullValue()));
        assertThat(found.getFirstName(), is(persist.getFirstName()));
        assertThat(found.getLastName(), is(persist.getLastName()));
        assertThat(found.getFavouriteColour(), is(colour));
        assertThat(found.getIsActive(), is(Boolean.TRUE));
        assertThat(found.getDateOfBirth(), is(dateOfBirth));
        assertThat(found.getHobbies(), is(persist.getHobbies()));
        assertThat(found.getCreatedOn(), is(persist.getCreatedOn()));
        assertThat(found.getLastModifiedOn(), is(persist.getLastModifiedOn()));
        assertThat(found.getCreatedBy(),  is(persist.getCreatedBy()));
        assertThat(found.getLastModifiedBy(),  is(persist.getLastModifiedBy()));
    }

    private Person createPerson() {
        final String firstName = "Ahmed";
        final String lastName = "Mukhtiar";
        return createPerson(firstName, lastName);
    }

    private Person createPerson(String firstName,  String lastName) {

        final Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setFavouriteColour(colour);
        person.setDateOfBirth(dateOfBirth);
        person.setHobbies(hobbies.subList(0,5));
        return person;
    }


}
