package com.hackerrank.assignment.controller;


import com.hackerrank.assignment.AssignmentApplication;
import com.hackerrank.assignment.domain.Person;
import com.hackerrank.assignment.dto.ListResponseDTO;
import com.hackerrank.assignment.dto.SearchCriteria;
import com.hackerrank.assignment.service.ColourService;
import com.hackerrank.assignment.service.HobbyService;
import com.hackerrank.assignment.service.PersonService;
import com.hackerrank.assignment.service.ServiceLocator;
import com.hackerrank.assignment.util.AssignmentHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.omg.PortableInterceptor.SUCCESSFUL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static com.hackerrank.assignment.TestUtils.createPerson;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { AssignmentApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class PersonControllerTest {

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
        return "http://localhost:" + port + "/api/1.0/person";
    }


    @WithMockUser("spring")
    @Test
    public void paginationTest() throws Exception {


        ListResponseDTO<Person> responseDTO = new ListResponseDTO();
        Person person = createPerson();
        responseDTO.setData(Arrays.asList(person));
        responseDTO.setTotalElement(1);
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("page", "0");
        requestParams.add("pageSize", "10");
        ResultActions result  = mockMvc.perform(get(getRootUrl() +"/pagination").params(requestParams));
        result.andExpect(status().isOk()).andExpect(jsonPath("$.status").value(AssignmentHelper.SUCCESS)).andDo(print());


    }

}
