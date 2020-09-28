/*
 * Copyright (C) 2017 Van Tibolli, All Rights Reserved.
 */
package com.hackerrank.assignment.service;

import com.hackerrank.assignment.annotations.LogMethod;
import com.hackerrank.assignment.domain.BaseEntity;
import com.hackerrank.assignment.dto.ListResponseDTO;
import com.hackerrank.assignment.dto.SearchCriteria;
import com.hackerrank.assignment.exception.AssignmentException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;


/**
 * The base service exposing the list method.
 *
 * @param <T> the generic entity type
 * @author mukhtiar.ahmed
 * @version 1.0
 */
public abstract class BaseListableService<T extends BaseEntity, ID> extends BaseService<T, ID>
        implements GenericListableService<T, ID> {

    /**
     * List all entities.
     *
     * @return the entity list
     * @throws AssignmentException if there is any error
     */
    @Override
    @LogMethod
    public List<T> list() {

        return repository.findAll();
    }

    @Override
    @LogMethod
    public ListResponseDTO<T> list(SearchCriteria searchCriteria) {
        ListResponseDTO<T> responseDTO = new ListResponseDTO<>();

        Pageable pageable;
        if (StringUtils.isEmpty(searchCriteria.getSortColumn())) {
            pageable = PageRequest.of(searchCriteria.getPage(), searchCriteria.getPageSize());
        } else {
            Sort sort = Sort.by(new Sort.Order(Sort.Direction.fromString(searchCriteria.getSortOrder()),
                    searchCriteria.getSortColumn()));
            pageable = PageRequest.of(searchCriteria.getPage(), searchCriteria.getPageSize(), sort);
        }
        Page<T> page = repository.findAll(pageable);

        responseDTO.setData(page.getContent());
        responseDTO.setTotalElement(page.getTotalElements());
        return responseDTO;

    }


}
