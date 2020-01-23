package com.hackerrank.assignment.repository;

import com.hackerrank.assignment.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The Person repository.
 *
 * @author mukhtiar.ahmed
 * @version 1.0
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, String> {

}
