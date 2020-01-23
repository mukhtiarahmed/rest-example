package com.hackerrank.assignment.service;

import com.hackerrank.assignment.annotations.LogMethod;
import com.hackerrank.assignment.domain.Hobby;
import com.hackerrank.assignment.exception.AssignmentException;
import com.hackerrank.assignment.repository.HobbyRepository;
import com.hackerrank.assignment.util.AssignmentHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The Hobby service
 *
 * @author mukhtiar.ahmed
 * @version 1.0
 */
@Slf4j
@Service
@Transactional
public class HobbyServiceImpl extends BaseListableService<Hobby, String> implements HobbyService {

    @Override
    @LogMethod
    public Hobby update(String id, Hobby entity) throws AssignmentException {
        AssignmentHelper.checkNull(id, "id");
        AssignmentHelper.checkNull(entity, "entity");
        Hobby oldEntity = get(id);
        oldEntity.setName(entity.getName());
        return repository.save(entity);
    }

    @Override
    public List<Hobby> activeHobbyList() {
        return ((HobbyRepository) repository).findByIsActiveTrue();
    }
}
