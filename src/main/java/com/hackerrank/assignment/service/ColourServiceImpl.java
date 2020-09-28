package com.hackerrank.assignment.service;

import com.hackerrank.assignment.annotations.LogMethod;
import com.hackerrank.assignment.domain.Colour;
import com.hackerrank.assignment.exception.AssignmentException;
import com.hackerrank.assignment.repository.ColourRepository;
import com.hackerrank.assignment.util.AssignmentHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The Colour service
 *
 * @author mukhtiar.ahmed
 * @version 1.0
 */

@Service
@Transactional
public class ColourServiceImpl extends BaseListableService<Colour, String> implements ColourService {


    @Override
    @LogMethod
    public Colour update(String id, Colour entity) throws AssignmentException {
        AssignmentHelper.checkNull(id, "id");
        AssignmentHelper.checkNull(entity, "entity");
        Colour oldEntity = get(id);
        oldEntity.setName(entity.getName());
        return repository.save(entity);
    }


    @Override
    @LogMethod
    public List<Colour> activeColourList() {


        return ((ColourRepository) repository).findByIsActiveTrue();
    }


}
