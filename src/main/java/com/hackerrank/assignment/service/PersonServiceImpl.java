package com.hackerrank.assignment.service;


import com.hackerrank.assignment.annotations.LogMethod;
import com.hackerrank.assignment.domain.Person;
import com.hackerrank.assignment.dto.ListResponseDTO;
import com.hackerrank.assignment.dto.SearchCriteria;
import com.hackerrank.assignment.exception.AssignmentException;
import com.hackerrank.assignment.util.AssignmentHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

/**
 * The Person service
 *
 * @author mukhtiar.ahmed
 * @version 1.0
 */

@Slf4j
@Service
@Transactional
public class PersonServiceImpl extends BaseListableService<Person, String> implements PersonService {

    private final List<String> columnCompMaster = Arrays.asList(
            "firstName", "lastName", "age");

    @Value("${page.size:10}")
    private int pageSize;


    @Override
    @LogMethod
    public Person update(String id, Person entity) throws AssignmentException {
        AssignmentHelper.checkNull(id, "id");
        AssignmentHelper.checkNull(entity, "entity");
        Person oldEntity = get(id);
        oldEntity.setLastName(entity.getLastName());
        oldEntity.setFirstName(entity.getFirstName());
        oldEntity.setDateOfBirth(entity.getDateOfBirth());
        oldEntity.setFavouriteColour(entity.getFavouriteColour());
        oldEntity.setHobbies(entity.getHobbies());
        return repository.save(oldEntity);
    }


    @Override
    @LogMethod
    public ListResponseDTO<Person> list(SearchCriteria searchCriteria) {

        Pageable pageable;
        if (columnCompMaster.contains(searchCriteria.getSortColumn())) {
            pageable = PageRequest.of(searchCriteria.getPage(), searchCriteria.getPageSize());
        } else {
            Sort sort = Sort.by(new Sort.Order(Sort.Direction.fromString(searchCriteria.getSortOrder()),
                    searchCriteria.getSortColumn()));
            pageable = PageRequest.of(searchCriteria.getPage(), searchCriteria.getPageSize() > 0 ?
                    searchCriteria.getPageSize() : pageSize, sort);
        }

        Page<Person> page;
        if (StringUtils.isEmpty(searchCriteria.getSearchString()) || searchCriteria.isActiveOnly())  {
            Person person = new Person();
            ExampleMatcher matcher = ExampleMatcher.matching();
            if (StringUtils.isEmpty(searchCriteria.getSearchString())) {
                person.setFirstName(searchCriteria.getSearchString());
                person.setLastName(searchCriteria.getSearchString());
                matcher.withIgnoreCase("firstName", "lastName")
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
            }

            if(searchCriteria.isActiveOnly()) {
                person.setIsActive(Boolean.TRUE);
                matcher.withMatcher("isActive", exact());
            }

            Example<Person> example = Example.of(person, matcher);
            page = repository.findAll(example, pageable);

        } else {
            page = repository.findAll(pageable);
        }



        ListResponseDTO responseDTO = new ListResponseDTO();

        responseDTO.setData(page.getContent());
        responseDTO.setTotalElement(page.getTotalElements());
        return responseDTO;

    }
}
