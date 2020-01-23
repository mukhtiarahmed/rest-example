/*
 * Copyright (C) 2017 Van Tibolli, All Rights Reserved.
 */
package com.hackerrank.assignment.service;


import com.hackerrank.assignment.domain.BaseEntity;
import com.hackerrank.assignment.exception.AssignmentException;
import com.hackerrank.assignment.exception.EntityNotFoundException;
import com.hackerrank.assignment.exception.InvalidDataException;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The generic service. Served as a base interface for CRUD operations.
 *
 * @param <T> the generic entity type
 * @author mukhtiar.ahmed
 * @version 1.0
 */
public interface GenericService<T extends BaseEntity, ID> {

    /**
     * This method is used to retrieve an entity.
     *
     * @param id the Id
     * @return the entity
     * @throws InvalidDataException    if id is null
     * @throws EntityNotFoundException if the entity does not exist
     * @throws AssignmentException     if any other error occurred during operation
     */
    T get(ID id) throws AssignmentException;

    /**
     * This method is used to create an entity.
     *
     * @param entity the entity
     * @return the created entity
     * @throws InvalidDataException if entity is null or not valid
     * @throws AssignmentException  if any other error occurred during operation
     */
    T create(T entity) throws AssignmentException;

    /**
     * This method is used to update an entity.
     *
     * @param id     the id
     * @param entity the entity
     * @return the updated entity
     * @throws InvalidDataException    if entity is null or not valid.
     * @throws EntityNotFoundException if the entity does not exist
     * @throws AssignmentException     if any other error occurred during operation
     */
    T update(ID id, T entity) throws AssignmentException;

    /**
     * This method is used to delete an entity.
     *
     * @param id the entity
     * @throws InvalidDataException    if id is null
     * @throws EntityNotFoundException if the entity does not exist
     * @throws AssignmentException     if any other error occurred during operation
     */
    void delete(ID id) throws AssignmentException;


    JpaRepository<T, ID> getRepository();


}
