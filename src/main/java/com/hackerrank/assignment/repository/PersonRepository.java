package com.hackerrank.assignment.repository;

import com.hackerrank.assignment.domain.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The Person repository.
 *
 * @author mukhtiar.ahmed
 * @version 1.0
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, String> {

   Page<Person> findByIsActiveTrue(Pageable pageable);

    @Query(value = "select p from person p where (lower(p.firstName) like %:search% or lower(p.lastName) like %:search%)" +
            " and isActive = true ")
    Page<Person> searchActivePersons(@Param("search") String search, Pageable pageable);

    @Query(value = "select p from person p where (lower(p.firstName) like %:search% or lower(p.lastName) like %:search%)")
    Page<Person> searchPersons(@Param("search") String search, Pageable pageable);

}
