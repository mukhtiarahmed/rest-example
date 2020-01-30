package com.hackerrank.assignment.repository;

import com.hackerrank.assignment.config.TestAuditingConfiguration;
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

}
