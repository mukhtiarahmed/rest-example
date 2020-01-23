package com.hackerrank.assignment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mukhtiar Ahmed
 */
@Data
@NoArgsConstructor
public class SimplePersonDTO implements Serializable {


    private String firstName;

    private String lastName;

    private String favouriteColour;

    private int age;

    private List<String> hobby;


}
