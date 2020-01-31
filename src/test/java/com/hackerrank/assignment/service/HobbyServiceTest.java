package com.hackerrank.assignment.service;

import com.hackerrank.assignment.config.TestAuditingConfiguration;
import com.hackerrank.assignment.domain.Hobby;
import com.hackerrank.assignment.repository.HobbyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
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
public class HobbyServiceTest {

    @Autowired
    private HobbyRepository hobbyRepository;

    @Autowired
    private HobbyService hobbyService;

    private Hobby hobby;
    @Before
    public void setup() throws Exception {
        String hobbyName = "Test Hobby";
        hobby = new Hobby();
        hobby.setName(hobbyName);
        hobby = hobbyRepository.save(hobby);
    }

    @Test
    public void testUpdatingAHobby() throws  Exception {
        String hobbyName = "Update Hobby Name";
        hobby.setName(hobbyName);
        Hobby updated = hobbyService.update(hobby.getId(), hobby);
        assertThat(updated, is(notNullValue()));
        assertThat(updated.getName(), is(hobbyName));
        assertThat(updated.getId(), is(hobby.getId()));
        assertThat(updated.getCreatedOn(), is(hobby.getCreatedOn()));
        assertThat(updated.getLastModifiedBy(), is(hobby.getLastModifiedBy()));
        assertThat(updated.getCreatedBy(), is(hobby.getCreatedBy()));
        assertThat(updated.getLastModifiedOn(), is(notNullValue()));
    }
}
