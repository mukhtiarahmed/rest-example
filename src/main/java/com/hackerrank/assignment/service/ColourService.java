package com.hackerrank.assignment.service;


import com.hackerrank.assignment.domain.Colour;

import java.util.List;

/**
 * The Colour service interface
 *
 * @author mukhtiar.ahmed
 * @version 1.0
 */

public interface ColourService extends GenericListableService<Colour, String> {


    List<Colour> activeColourList();

}
