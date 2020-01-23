package com.hackerrank.assignment.service;


import com.hackerrank.assignment.domain.Hobby;

import java.util.List;

/**
 * The Hobby service interface
 *
 * @author mukhtiar.ahmed
 * @version 1.0
 */

public interface HobbyService extends GenericListableService<Hobby, String> {


    List<Hobby> activeHobbyList();


}
