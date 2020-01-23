package com.hackerrank.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO<T> implements Serializable {

    private static final long serialVersionUID = 8414024608947196037L;

    private String status;

    private String message;

    private T data;


}
