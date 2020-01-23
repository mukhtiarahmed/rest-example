package com.hackerrank.assignment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ColourDTO implements Serializable {

    private String id;

    private String name;

    private String hex;
}
