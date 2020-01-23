package com.hackerrank.assignment.controller;


import com.hackerrank.assignment.annotations.LogMethod;
import com.hackerrank.assignment.domain.Colour;
import com.hackerrank.assignment.dto.ColourDTO;
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
 * The Colour REST controller.
 *
 * @author mukhtiar.ahmed
 * @version 1.0
 */
@RestController
@RequestMapping(API_VER + "/colour")
public class ColourController {


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
     * Get All Colours.
     *
     * @return the ResponseDTO
     */
    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    @LogMethod
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDTO<List> getAll() throws AssignmentException {
        List<Colour> colours = serviceLocator.getColourService().activeColourList();
        List<ColourDTO> colourDTOS = colours.stream().map(c ->
                AssignmentMapper.toColourDTO(c)).collect(Collectors.toList());
        return new ResponseDTO<>(SUCCESS, null, colourDTOS);
    }

    /**
     * Get a single Colour.
     *
     * @param id the Colour ID
     * @return the ResponseDTO
     * @throws InvalidDataException    if id is not positive
     * @throws EntityNotFoundException if the entity does not exist
     * @throws AssignmentException     if any other error occurred during operation
     */
    @RequestMapping(value = "/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    @LogMethod
    public ResponseDTO<ColourDTO> get(@PathVariable String id) throws AssignmentException {
        Colour colour = serviceLocator.getColourService().get(id);
        return new ResponseDTO<>(SUCCESS, null, AssignmentMapper.toColourDTO(colour));
    }

    /**
     * Create the Colour.
     *
     * @param dto the ColourDTO dto.
     * @return the ResponseDTO
     * @throws InvalidDataException if entity is null or not valid
     * @throws AssignmentException  if any other error occurred during operation
     */
    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    @LogMethod
    public ResponseDTO<ColourDTO> create(@RequestBody ColourDTO dto) throws AssignmentException {
        Colour entity = new Colour();
        entity.setName(dto.getName());
        entity.setHex(dto.getHex());
        Colour colour = serviceLocator.getColourService().create(entity);
        return new ResponseDTO<>(SUCCESS, null, AssignmentMapper.toColourDTO(colour));
    }


    /**
     * Update the Colour.
     *
     * @param id     the Colour ID
     * @param dto the ColourDTO dto.
     * @return the ResponseDTO
     * @throws InvalidDataException if entity is null or not valid, or id is not positive
     * @throws AssignmentException  if any other error occurred during operation
     */
    @RequestMapping(value = "/{id}", method = PUT, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @LogMethod
    public ResponseDTO<ColourDTO> update(@PathVariable String id, @RequestBody ColourDTO dto) throws AssignmentException {
        Colour entity = new Colour();
        entity.setName(dto.getName());
        entity.setHex(dto.getHex());
        Colour colour = serviceLocator.getColourService().update(id, entity);
        return new ResponseDTO<>(SUCCESS, null, AssignmentMapper.toColourDTO(colour));
    }

    /**
     * Delete the Colour.
     *
     * @param id the Colour ID
     * @throws InvalidDataException if id is not positive
     * @throws AssignmentException  if any other error occurred during operation
     */
    @RequestMapping(value = "/{id}", method = DELETE)
    @LogMethod
    public ResponseDTO delete(@PathVariable String id) throws AssignmentException {
        serviceLocator.getColourService().delete(id);
        return new ResponseDTO(SUCCESS, "Delete Colour", null);
    }
}
