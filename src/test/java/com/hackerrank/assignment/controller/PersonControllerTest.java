package com.hackerrank.assignment.controller;


import com.hackerrank.assignment.AssignmentApplication;
import com.hackerrank.assignment.TestUtils;
import com.hackerrank.assignment.config.TestAuditingConfiguration;
import com.hackerrank.assignment.domain.Colour;
import com.hackerrank.assignment.domain.Hobby;
import com.hackerrank.assignment.domain.Person;
import com.hackerrank.assignment.dto.ListResponseDTO;
import com.hackerrank.assignment.dto.PersonDTO;
import com.hackerrank.assignment.dto.SearchCriteria;
import com.hackerrank.assignment.exception.AssignmentException;
import com.hackerrank.assignment.repository.ColourRepository;
import com.hackerrank.assignment.repository.HobbyRepository;
import com.hackerrank.assignment.repository.PersonRepository;
import com.hackerrank.assignment.service.BaseListableService;
import com.hackerrank.assignment.service.BaseService;
import com.hackerrank.assignment.service.ColourService;
import com.hackerrank.assignment.service.HobbyService;
import com.hackerrank.assignment.service.PersonService;
import com.hackerrank.assignment.service.PersonServiceImpl;
import com.hackerrank.assignment.service.ServiceLocator;
import com.hackerrank.assignment.util.AssignmentHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.omg.PortableInterceptor.SUCCESSFUL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import java.util.Arrays;
import java.util.List;

import static com.hackerrank.assignment.TestUtils.createPerson;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@Sql({"classpath:import.sql"})
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@SpringBootTest(classes = { AssignmentApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext context;


    private MockMvc mockMvc;


    @Before
    public void setup() throws AssignmentException {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();


    }

    private String getRootUrl() {
        return "http://localhost:" + port + "/api/1.0/person";
    }


    @WithMockUser
    @Test
    public void paginationTest() throws Exception {

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("page", "0");
        requestParams.add("pageSize", "10");
        ResultActions result  = mockMvc.perform(get(getRootUrl() +"/pagination").params(requestParams));
        result.andExpect(status().isOk()).andExpect(jsonPath("$.status").value(AssignmentHelper.SUCCESS)).andDo(print());

    }


    @Test
    @WithMockUser(username = "ahmed", roles = {"ADMIN"})
    public void paginationAdminTest() throws Exception {

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("page", "0");
        requestParams.add("pageSize", "10");
        ResultActions result  = mockMvc.perform(get(getRootUrl() + "/admin/pagination").params(requestParams));
        result.andExpect(status().isOk()).andExpect(jsonPath("$.status").value(AssignmentHelper.SUCCESS)).andDo(print());

    }


    @Test
    @WithMockUser(username="ahmed",roles={"ADMIN"})
    public void createPersonTest() throws Exception {

        PersonDTO dto = TestUtils.createPersonDTO();
        String personJson = TestUtils.convertObjectToJson(dto);
        ResultActions result  = mockMvc.perform(post(getRootUrl()).with(csrf()).with(user("ahmed").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(personJson));

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(AssignmentHelper.SUCCESS))
                .andExpect(jsonPath("$.data.firstName").value(dto.getFirstName())).andDo(print());

    }


}
