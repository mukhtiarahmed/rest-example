package com.hackerrank.assignment.controller;

import com.hackerrank.assignment.AssignmentApplication;
import com.hackerrank.assignment.exception.AssignmentException;
import com.hackerrank.assignment.util.AssignmentHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The Hobby Controller Test class.
 *
 * @author mukhtiar.ahmed
 */
@Slf4j
@RunWith(SpringRunner.class)
@Sql({"classpath:import.sql"})
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@SpringBootTest(classes = { AssignmentApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class HobbyControllerTest {


    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext context;


    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }
    private String getRootUrl() {
        return "http://localhost:" + port + "/api/1.0/hobby";
    }


    @WithMockUser
    @Test
    public void findAllHobbyTest() throws Exception {
        ResultActions result  = mockMvc.perform(get(getRootUrl() ));
        result.andExpect(status().isOk()).andExpect(jsonPath("$.status").value(AssignmentHelper.SUCCESS)).andDo(print());
    }

}