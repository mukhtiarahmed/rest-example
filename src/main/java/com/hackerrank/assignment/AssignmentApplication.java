package com.hackerrank.assignment;

import com.hackerrank.assignment.repository.ColourRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;


/**
 * The Assignment Application.
 *
 * @author mukhtiar.ahmed
 * @version 1.0
 */
@Slf4j
@SpringBootApplication
public class AssignmentApplication {

    private static final String LOOKUP_DATA = "classpath:lookup-data.sql";

    @Autowired
    private ColourRepository colourRepository;

    @Autowired
    private DataSource datasource;

    @Autowired
    private ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(AssignmentApplication.class, args);
    }

    @Bean
    @Profile("default")
    InitializingBean sendDatabase() {
        return () -> {
            long count = colourRepository.count();
            if (count == 0) {
                Resource resource = context.getResource(LOOKUP_DATA);
                ScriptUtils.executeSqlScript(datasource.getConnection(), resource);
            }
        };
    }


}
