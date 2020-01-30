package com.hackerrank.assignment.repository;

import com.hackerrank.assignment.config.TestAuditingConfiguration;
import com.hackerrank.assignment.domain.Colour;
import com.hackerrank.assignment.domain.Person;
import lombok.extern.slf4j.Slf4j;
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

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
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
public class ColourRepositoryTest {

    @Autowired
    private ColourRepository colourRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AuditorAware<String> auditorAware;

    @Test
    public void testCurrentAuditor() {
        String currentAuditor = auditorAware.getCurrentAuditor().get();
        assertEquals("Test  Auditor", currentAuditor);
    }

    @Test
    public void testCreatingAColour() throws Exception {
        String colourName = "Test Colour";
        String hex = "#00FF00";
        final Colour colour = new Colour();
        colour.setHex(hex);
        colour.setName(colourName);
        final Colour persist = colourRepository.save(colour);
        assertThat(persist.getId(), is(notNullValue()));
        assertThat(persist.getName(), is(colourName));
        assertThat(persist.getHex(), is(hex));
        assertThat(persist.getLastModifiedOn(), is(notNullValue()));
        assertThat(persist.getCreatedOn(), is(notNullValue()));
        assertThat(persist.getLastModifiedBy(), is(notNullValue()));
        assertThat(persist.getCreatedBy(), is(notNullValue()));

    }

    @Test
    public void testUpdatingAColour() throws Exception {
        final Colour colour = new Colour();
        colour.setHex( "#000000");
        colour.setName("Old Colour");
        final Colour persist = entityManager.persist(colour);

        final String colourName = "Updated Colour";
        final String hex = "#00FF00";
        persist.setHex(hex);
        persist.setName(colourName);
        final Colour update = colourRepository.save(persist);
        assertThat(update.getId(), is(persist.getId()));
        assertThat(update.getName(), is(colourName));
        assertThat(update.getHex(), is(hex));
        assertThat(update.getLastModifiedOn(), is(notNullValue()));
        assertThat(update.getCreatedOn(), is(notNullValue()));
        assertThat(update.getLastModifiedBy(), is(notNullValue()));
        assertThat(update.getCreatedBy(), is(notNullValue()));

    }

    @Test
    public void testFindAColour() throws Exception {
        String colourName = "Test Colour";
        String hex = "#00FF00";
        final Colour colour = new Colour();
        colour.setHex(hex);
        colour.setName(colourName);
        final Colour persist = entityManager.persist(colour);
        final Optional<Colour>  optional = colourRepository.findById(persist.getId());
        assertThat(optional.isPresent(), is(Boolean.TRUE));
        assertThat(optional, is(Optional.of(persist)));
        final Colour found = optional.get();
        assertThat(found.getId(), is(persist.getId()));
        assertThat(found.getName(), is(colourName));
        assertThat(found.getHex(), is(hex));
        assertThat(found.getLastModifiedOn(), is(notNullValue()));
        assertThat(found.getCreatedOn(), is(notNullValue()));
        assertThat(found.getLastModifiedBy(), is(notNullValue()));
        assertThat(found.getCreatedBy(), is(notNullValue()));

    }


    @Test
    public void testFindAllColour() throws Exception {
        String colourName = "Test Colour";
        String hex = "#00FF00";
        final Colour colour = new Colour();
        colour.setHex(hex);
        colour.setName(colourName);
        entityManager.persist(colour);
        List<Colour> colours = entityManager.getEntityManager().createQuery(
                "SELECT c FROM  colour c", Colour.class).getResultList();


        final List<Colour> findAll = colourRepository.findAll();
        assertThat( colours, hasItem(hasProperty("name", is(colourName))));
        assertThat( colours, hasItem(hasProperty("hex", is(hex))));
        assertThat(colours.size(), is(findAll.size()));


    }
}
