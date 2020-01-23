package com.hackerrank.assignment.repository;

import com.hackerrank.assignment.domain.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Hobby repository.
 *
 * @author mukhtiar.ahmed
 * @version 1.0
 */
@Repository
public interface HobbyRepository extends JpaRepository<Hobby, String> {


    List<Hobby> findByIsActiveTrue();
}
