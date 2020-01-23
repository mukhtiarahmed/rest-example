/*
 * Copyright (C) 2017 Van Tibolli, All Rights Reserved.
 */
package com.hackerrank.assignment.service;


import com.hackerrank.assignment.annotations.LogMethod;
import com.hackerrank.assignment.domain.BaseEntity;
import com.hackerrank.assignment.exception.ConfigurationException;
import com.hackerrank.assignment.exception.EntityNotFoundException;
import com.hackerrank.assignment.exception.InvalidDataException;
import com.hackerrank.assignment.util.AssignmentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.PostConstruct;
import java.util.Optional;

import static com.hackerrank.assignment.util.AssignmentHelper.checkConfigNotNull;


/**
 * This is a base class for services that provides basic CRUD capabilities.
 *
 * @param <T> the generic entity type
 * @author mukhtiar.ahmed
 * @version 1.0
 */

public abstract class BaseService<T extends BaseEntity, ID> implements GenericService<T, ID> {

    /**
     * The repository.
     */
    @Autowired
    protected JpaRepository<T, ID> repository;


    /**
     * Check the configuration.
     *
     * @throws ConfigurationException if there's any error in configuration
     */
    @PostConstruct
    public void checkConfiguration() {
        checkConfigNotNull(repository, "repository");
    }

    /**
     * This method is used to retrieve an entity.
     *
     * @param id the Id
     * @return the entity
     * @throws InvalidDataException    if id is null
     * @throws EntityNotFoundException if the entity does not exist
     */
    @Override
    @LogMethod
    public T get(ID id) {
        AssignmentHelper.checkNull(id, "id");
        Optional<T> optional = repository.findById(id);
        T entity = optional.get();
        AssignmentHelper.checkEntityExist(entity, id);
        return entity;
    }

    /**
     * This method is used to create an entity.
     *
     * @param entity the entity
     * @return the created entity
     * @throws InvalidDataException if entity is null or not valid
     */
    @Override
    @LogMethod
    public T create(T entity) {
        AssignmentHelper.checkNull(entity, "entity");
        return repository.save(entity);
    }

    /**
     * This method is used to delete an entity.
     *
     * @param id the entity
     * @throws InvalidDataException if id is null;
     */
    @Override
    @LogMethod
    public void delete(ID id) {
        AssignmentHelper.checkNull(id, "id");
        T entity = get(id);
        entity.setIsActive(false);
        repository.save(entity);
    }

    public JpaRepository<T, ID> getRepository() {
        return repository;
    }

}
