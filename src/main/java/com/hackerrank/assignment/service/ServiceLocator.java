package com.hackerrank.assignment.service;

import com.hackerrank.assignment.exception.ConfigurationException;
import com.hackerrank.assignment.util.AssignmentHelper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * The Service Locator
 *
 * @author mukhtiar.ahmed
 * @version 1.0
 */
@Getter
@Component
public class ServiceLocator {


    @Autowired
    private PersonService personService;

    @Autowired
    private HobbyService hobbyService;

    @Autowired
    private ColourService colourService;


    /**
     * Check the configuration.
     *
     * @throws ConfigurationException if there's any error in configuration
     */
    @PostConstruct
    public void checkConfiguration() {
        AssignmentHelper.checkConfigNotNull(personService, "personService");
        AssignmentHelper.checkConfigNotNull(hobbyService, "hobbyService");
        AssignmentHelper.checkConfigNotNull(colourService, "colourService");
    }


}
