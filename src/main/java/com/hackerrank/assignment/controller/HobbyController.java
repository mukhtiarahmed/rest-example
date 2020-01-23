package com.hackerrank.assignment.controller;

import com.hackerrank.assignment.annotations.LogMethod;
import com.hackerrank.assignment.domain.Hobby;
import com.hackerrank.assignment.dto.HobbyDTO;
import com.hackerrank.assignment.dto.ResponseDTO;
import com.hackerrank.assignment.exception.AssignmentException;
import com.hackerrank.assignment.exception.ConfigurationException;
import com.hackerrank.assignment.exception.EntityNotFoundException;
import com.hackerrank.assignment.exception.InvalidDataException;
import com.hackerrank.assignment.mapper.AssignmentMapper;
import com.hackerrank.assignment.service.ServiceLocator;
import com.hackerrank.assignment.util.AssignmentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.hackerrank.assignment.util.AssignmentHelper.API_VER;
import static com.hackerrank.assignment.util.AssignmentHelper.SUCCESS;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;


/**
 * The Hobby REST controller.
 *
 * @author mukhtiar.ahmed
 * @version 1.0
 */

@RestController
@RequestMapping(API_VER + "/hobby")
public class HobbyController {

    @Autowired
    private ServiceLocator serviceLocator;


    /**
     * Check the configuration.
     *
     * @throws ConfigurationException if there's any error in configuration
     */
    @PostConstruct
    public void checkConfiguration() {
        AssignmentHelper.checkConfigNotNull(serviceLocator, "serviceLocator");
    }

    /**
     * Get All Hobbies.
     *
     * @return the ResponseDTO
     */
    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    @LogMethod
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDTO<List> getAll() throws AssignmentException {
        List<Hobby> hobbyList = serviceLocator.getHobbyService().activeHobbyList();
        List<HobbyDTO> hobbyDTOS = hobbyList.stream().map(h ->
                AssignmentMapper.toHobbyDTO(h)).collect(Collectors.toList());
        return new ResponseDTO<>(SUCCESS, null, hobbyDTOS);
    }

    /**
     * Get a single Hobby.
     *
     * @param id the Hobby ID
     * @return the Hobby entity
     * @throws InvalidDataException    if id is not positive
     * @throws EntityNotFoundException if the entity does not exist
     * @throws AssignmentException     if any other error occurred during operation
     */
    @RequestMapping(value = "/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    @LogMethod
    public ResponseDTO<HobbyDTO> get(@PathVariable String id) throws AssignmentException {
        Hobby hobby = serviceLocator.getHobbyService().get(id);
        return new ResponseDTO<>(SUCCESS, null, AssignmentMapper.toHobbyDTO(hobby));
    }

    /**
     * Create the Hobby.
     *
     * @param entity the Hobby entity.
     * @return the entity
     * @throws InvalidDataException if entity is null or not valid
     * @throws AssignmentException  if any other error occurred during operation
     */
    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    @LogMethod
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDTO<HobbyDTO> create(@RequestBody @Valid Hobby entity) throws AssignmentException {
        Hobby hobby = serviceLocator.getHobbyService().create(entity);
        return new ResponseDTO<>(SUCCESS, null, AssignmentMapper.toHobbyDTO(hobby));
    }


    /**
     * Update the Hobby.
     *
     * @param id     the Hobby ID
     * @param entity the Hobby entity
     * @return the updated entity
     * @throws InvalidDataException if entity is null or not valid, or id is not positive
     * @throws AssignmentException  if any other error occurred during operation
     */
    @RequestMapping(value = "/{id}", method = PUT, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @LogMethod
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDTO<HobbyDTO> update(@PathVariable String id, @RequestBody @Valid Hobby entity) throws AssignmentException {
        Hobby hobby = serviceLocator.getHobbyService().update(id, entity);
        return new ResponseDTO<>(SUCCESS, null, AssignmentMapper.toHobbyDTO(hobby));
    }

    /**
     * Delete the Hobby.
     *
     * @param id the Hobby ID
     * @throws InvalidDataException if id is not positive
     * @throws AssignmentException  if any other error occurred during operation
     */
    @RequestMapping(value = "/{id}", method = DELETE)
    @LogMethod
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDTO delete(@PathVariable String id) throws AssignmentException {
        serviceLocator.getHobbyService().delete(id);
        return new ResponseDTO(SUCCESS, "Delete Hobby", null);
    }
}
