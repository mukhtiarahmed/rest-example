package com.hackerrank.assignment.repository;

import com.hackerrank.assignment.domain.Colour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Colour repository.
 *
 * @author mukhtiar.ahmed
 * @version 1.0
 */
@Repository
public interface ColourRepository extends JpaRepository<Colour, String> {


    List<Colour> findByIsActiveTrue();
}
