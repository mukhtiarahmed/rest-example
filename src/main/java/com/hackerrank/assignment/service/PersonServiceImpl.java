package com.hackerrank.assignment.service;


import com.hackerrank.assignment.annotations.LogMethod;
import com.hackerrank.assignment.domain.Person;
import com.hackerrank.assignment.dto.ListResponseDTO;
import com.hackerrank.assignment.dto.SearchCriteria;
import com.hackerrank.assignment.exception.AssignmentException;
import com.hackerrank.assignment.repository.PersonRepository;
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
            Sort sort = Sort.by(new Sort.Order(Sort.Direction.fromString(searchCriteria.getSortOrder()),
                    searchCriteria.getSortColumn()));
            pageable = PageRequest.of(searchCriteria.getPage(), searchCriteria.getPageSize() > 0 ?
                    searchCriteria.getPageSize() : pageSize, sort);
        } else {
            pageable = PageRequest.of(searchCriteria.getPage(), searchCriteria.getPageSize());
        }

        Page<Person> page;
        if (!StringUtils.isEmpty(searchCriteria.getSearchString())) {
            String searchString = searchCriteria.getSearchString().toLowerCase();
            if (searchCriteria.isActiveOnly()) {
                page = ((PersonRepository) repository).searchActivePersons(searchString, pageable);
            } else {
                page = ((PersonRepository) repository).searchPersons(searchString, pageable);
            }
        } else  if (searchCriteria.isActiveOnly())  {
            page = ((PersonRepository) repository).findByIsActiveTrue(pageable);
        } else {
            page = repository.findAll(pageable);
        }

        ListResponseDTO responseDTO = new ListResponseDTO();

        responseDTO.setData(page.getContent());
        responseDTO.setTotalElement(page.getTotalElements());
        return responseDTO;

    }
}
