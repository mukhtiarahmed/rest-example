package com.hackerrank.assignment.controller;


import com.hackerrank.assignment.annotations.LogMethod;
import com.hackerrank.assignment.domain.Hobby;
import com.hackerrank.assignment.domain.Person;
import com.hackerrank.assignment.dto.*;
import com.hackerrank.assignment.exception.AssignmentException;
import com.hackerrank.assignment.exception.ConfigurationException;
import com.hackerrank.assignment.exception.EntityNotFoundException;
import com.hackerrank.assignment.exception.InvalidDataException;
import com.hackerrank.assignment.mapper.AssignmentMapper;
import com.hackerrank.assignment.service.ServiceLocator;
import com.hackerrank.assignment.util.AssignmentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.hackerrank.assignment.util.AssignmentHelper.API_VER;
import static com.hackerrank.assignment.util.AssignmentHelper.SUCCESS;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;


/**
 * The Person REST controller.
 *
 * @author mukhtiar.ahmed
 * @version 1.0
 */
@RestController
@RequestMapping(API_VER + "/person")
public class PersonController {

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


    @LogMethod
    @RequestMapping(value = "/pagination",
            method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ListResponseDTO<SimplePersonDTO> pagination(@RequestParam(value = "orderBy", required = false) String orderBy,
                                                       @RequestParam(value = "direction", required = false) String direction,
                                                       @RequestParam("page") int page, @RequestParam("pageSize") int pageSize,
                                                       @RequestParam(value = "search", required = false) String search) {

        SearchCriteria searchCriteria = SearchCriteria.builder()
                .withSearchString(search).
                        withPageSize(pageSize).
                        withPage(page).
                        withSortColumn(orderBy).
                        withSortOrder(direction).withActiveOnly(Boolean.TRUE).build();
        ListResponseDTO<Person> listResponseDTO = serviceLocator.getPersonService().list(searchCriteria);
        List<SimplePersonDTO> dtoList = listResponseDTO.getData().stream().map(p ->
                AssignmentMapper.toSimplePersonDTO(p)).collect(Collectors.toList());

        ListResponseDTO<SimplePersonDTO> responseDTO = new ListResponseDTO<>();
        responseDTO.setData(dtoList);
        responseDTO.setTotalElement(listResponseDTO.getTotalElement());
        responseDTO.setStatus(SUCCESS);
        return responseDTO;

    }

    @LogMethod
    @RequestMapping(value = "/admin/pagination",
            method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ListResponseDTO<PersonDTO> paginationForAdmin(@RequestParam(value = "orderBy", required = false) String orderBy,
                                                         @RequestParam(value = "direction", required = false) String direction,
                                                         @RequestParam("page") int page, @RequestParam("pageSize") int pageSize,
                                                         @RequestParam(value = "search", required = false) String search) {

        SearchCriteria searchCriteria = SearchCriteria.builder()
                .withSearchString(search).
                        withPageSize(pageSize).
                        withPage(page).
                        withSortColumn(orderBy).
                        withSortOrder(direction).build();
        ListResponseDTO<Person> listResponseDTO = serviceLocator.getPersonService().list(searchCriteria);
        List<PersonDTO> dtoList = listResponseDTO.getData().stream().map(
                AssignmentMapper::toPersonDTO).collect(Collectors.toList());

        ListResponseDTO<PersonDTO> responseDTO = new ListResponseDTO<>();
        responseDTO.setData(dtoList);
        responseDTO.setTotalElement(listResponseDTO.getTotalElement());
        responseDTO.setStatus(SUCCESS);
        return responseDTO;
    }


    /**
     * Get a single Person.
     *
     * @param id the Person ID
     * @return the ResponseDTO
     * @throws InvalidDataException    if id is not positive
     * @throws EntityNotFoundException if the entity does not exist
     * @throws AssignmentException     if any other error occurred during operation
     */
    @RequestMapping(value = "/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    @LogMethod
    @ResponseBody
    public ResponseDTO<SimplePersonDTO> get(@PathVariable String id) throws AssignmentException {
        Person person = serviceLocator.getPersonService().get(id);
        return new ResponseDTO<>(SUCCESS, null, AssignmentMapper.toSimplePersonDTO(person));
    }

    /**
     * Get a single Person.
     *
     * @param id the Person ID
     * @return the ResponseDTO
     * @throws InvalidDataException    if id is not positive
     * @throws EntityNotFoundException if the entity does not exist
     * @throws AssignmentException     if any other error occurred during operation
     */
    @RequestMapping(value = "/admin/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    @LogMethod
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseDTO<PersonDTO> getPersonDTO(@PathVariable String id) throws AssignmentException {
        Person person = serviceLocator.getPersonService().get(id);
        return new ResponseDTO<>(SUCCESS, null, AssignmentMapper.toPersonDTO(person));
    }


    /**
     * Create the Person.
     *
     * @param personDTO the PersonDTO.
     * @return the ResponseDTO
     * @throws InvalidDataException if entity is null or not valid
     * @throws AssignmentException  if any other error occurred during operation
     */
    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    @LogMethod
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseDTO<PersonDTO> create(@RequestBody @Valid PersonDTO personDTO) throws AssignmentException {
        Person entity = toPerson(personDTO);
        Person person = serviceLocator.getPersonService().create(entity);
        return new ResponseDTO<>(SUCCESS, null, AssignmentMapper.toPersonDTO(person));
    }


    /**
     * Update the Person.
     *
     * @param id        The Person ID
     * @param personDTO the PersonDTO
     * @return the ResponseDTO
     * @throws InvalidDataException if entity is null or not valid, or id is not positive
     * @throws AssignmentException  if any other error occurred during operation
     */
    @RequestMapping(value = "/{id}", method = PUT, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @LogMethod
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseDTO<PersonDTO> update(@PathVariable String id, @Valid @RequestBody PersonDTO personDTO) throws AssignmentException {
        Person entity = toPerson(personDTO);
        Person person = serviceLocator.getPersonService().update(id, entity);
        return new ResponseDTO<>(SUCCESS, null, AssignmentMapper.toPersonDTO(person));
    }

    /**
     * Delete the Person.
     *
     * @param id the Person ID
     * @throws InvalidDataException if id is not positive
     * @throws AssignmentException  if any other error occurred during operation
     */
    @RequestMapping(value = "/{id}", method = DELETE)
    @LogMethod
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseDTO<String> delete(@PathVariable String id) throws AssignmentException {
        serviceLocator.getPersonService().delete(id);
        return new ResponseDTO<>(SUCCESS, "Delete Person", null);
    }


    private Person toPerson(PersonDTO dto) throws AssignmentException {
        Person entity = new Person();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setFavouriteColour(serviceLocator.getColourService().get(dto.getColourId()));
        List<Hobby> hobbies = new ArrayList<>(dto.getHobbies().size());
        for (String id : dto.getHobbies()) {
            hobbies.add(serviceLocator.getHobbyService().get(id));
        }

        entity.setHobbies(hobbies);
        if (entity.getDateOfBirth() != null) {
            entity.setDateOfBirth(dto.getDateOfBirth());
        } else if (dto.getAge() > 0) {
            LocalDate dateOfBirth = LocalDate.now().minusYears(dto.getAge()).withMonth(Month.JANUARY.getValue())
                    .withDayOfMonth(1);
            entity.setDateOfBirth(dateOfBirth);
        }

        return entity;
    }

}



