package com.hackerrank.assignment.service;

import com.hackerrank.assignment.TestUtils;
import com.hackerrank.assignment.config.TestAuditingConfiguration;
import com.hackerrank.assignment.domain.Colour;
import com.hackerrank.assignment.exception.AssignmentException;
import com.hackerrank.assignment.repository.ColourRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * The Hobby Service Test class.
 *
 * @author mukhtiar.ahmed
 */
@Slf4j
@RunWith(SpringRunner.class)
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@Sql({"classpath:import.sql"})
@SpringBootTest
public class ColourServiceTest {

    @Autowired
    private ColourRepository colourRepository;

    @Autowired
    private ColourService colourService;

    private  Colour colour;

    @Before
    public void setup() throws AssignmentException {
        MockitoAnnotations.initMocks(this);
        colour = colourRepository.save(TestUtils.createColour());
    }

    @Test
    public void testUpdatingAColour() throws  Exception {
        String colourName = "Update Colour Name";
        String hex = "#000000";
        colour.setName(colourName);
        colour.setHex(hex);

        Colour updated  = colourService.update(colour.getId(), colour);
        assertThat(updated.getId(), is(colour.getId()));
        assertThat(updated.getName(), is(colourName));
        assertThat(updated.getHex(), is(hex));
        assertThat(updated.getCreatedOn(), is(colour.getCreatedOn()));
        assertThat(updated.getCreatedBy(), is(colour.getCreatedBy()));
        assertThat(updated.getLastModifiedBy(), is(notNullValue()));
        assertThat(updated.getLastModifiedOn(), is(notNullValue()));
    }

}
