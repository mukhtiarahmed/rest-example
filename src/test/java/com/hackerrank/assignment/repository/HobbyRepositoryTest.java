package com.hackerrank.assignment.repository;

import com.hackerrank.assignment.config.TestAuditingConfiguration;
import com.hackerrank.assignment.domain.Hobby;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.core.IsNot;
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
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertEquals;
/**
 * The Hobby Repository Test class.
 *
 * @author mukhtiar.ahmed
 */
@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
@Import(TestAuditingConfiguration.class)
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class HobbyRepositoryTest {

    @Autowired
    private AuditorAware<String> auditorAware;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private HobbyRepository hobbyRepository;

    @Test
    public void testCurrentAuditor() {
        String currentAuditor = auditorAware.getCurrentAuditor().get();
        assertEquals("Test  Auditor", currentAuditor);
    }


    @Test
    public void testCreatingAHobby() throws Exception {
        String hobbyName = "Test Hobby";
        Hobby hobby = new Hobby();
        hobby.setName(hobbyName);
        Hobby persist  = hobbyRepository.save(hobby);
        assertThat(persist, is(notNullValue()));
        assertThat(persist.getId(), is(notNullValue()));
        assertThat(persist.getName(), is(hobbyName));
        assertThat(persist.getLastModifiedOn(), is(notNullValue()));
        assertThat(persist.getCreatedOn(), is(notNullValue()));
        assertThat(persist.getLastModifiedBy(), is(notNullValue()));
        assertThat(persist.getCreatedBy(), is(notNullValue()));

        Optional<Hobby> optional = hobbyRepository.findById(persist.getId());
        assertThat(optional.isPresent(), is(Boolean.TRUE));
        assertThat(optional, is(Optional.of(hobby)));
        Hobby found = optional.get();
        assertThat(found.getName(), is(hobbyName));
        assertThat(found, is(notNullValue()));
        assertThat(found.getId(), is(notNullValue()));
        assertThat(found.getLastModifiedOn(), is(notNullValue()));
        assertThat(found.getCreatedOn(), is(notNullValue()));
        assertThat(found.getLastModifiedBy(), is(notNullValue()));
        assertThat(found.getCreatedBy(), is(notNullValue()));

    }

    @Test
    public void testUpdatingAHobby() throws Exception {

        Hobby hobby = new Hobby();
        hobby.setName( "Old Hobby");
        Hobby persist  = entityManager.persist(hobby);
        Hobby old = entityManager.find(Hobby.class, persist.getId());
        String hobbyName = "Test Hobby";
        old.setName(hobbyName);
        Hobby updated = hobbyRepository.save(old);
        assertThat(updated, is(notNullValue()));
        assertThat(updated.getName(), is(hobbyName));
        assertThat(updated.getId(), is(old.getId()));
        assertThat(updated.getCreatedOn(), is(old.getCreatedOn()));
        assertThat(updated.getLastModifiedBy(), is(old.getLastModifiedBy()));
        assertThat(updated.getCreatedBy(), is(old.getCreatedBy()));
        assertThat(updated.getLastModifiedOn(), is(old.getLastModifiedOn()));

    }
    @Test
    public void testFindByIdHobby() throws Exception {
        String hobbyName = "Test Hobby";
        Hobby hobby = new Hobby();
        hobby.setName(hobbyName);
        Hobby persist  = entityManager.persist(hobby);

        Optional<Hobby> optional = hobbyRepository.findById(persist.getId());
        assertThat(optional.isPresent(), is(Boolean.TRUE));
        assertThat(optional, is(Optional.of(persist)));
        final Hobby found = optional.get();
        assertThat(found, is(notNullValue()));
        assertThat(found.getName(), is(hobbyName));
        assertThat(found.getId(), is(persist.getId()));
        assertThat(found.getCreatedOn(), is(persist.getCreatedOn()));
        assertThat(found.getLastModifiedBy(), is(persist.getLastModifiedBy()));
        assertThat(found.getCreatedBy(), is(persist.getCreatedBy()));
        assertThat(found.getLastModifiedOn(), is(persist.getLastModifiedOn()));

    }

    @Test
    public void testFindAllHobby() throws Exception {
        List<Hobby> beforeAdd =  hobbyRepository.findAll();
        String hobbyName = "Test Hobby";
        Hobby hobby = new Hobby();
        hobby.setName(hobbyName);
        Hobby persist  = hobbyRepository.save(hobby);
        List<Hobby> afterAdd =  hobbyRepository.findAll();
        assertThat( afterAdd, hasItem(
                hasProperty("name", is(hobbyName) )
        ));
        assertThat(afterAdd.size(), is(beforeAdd.size() +1));

    }


}
