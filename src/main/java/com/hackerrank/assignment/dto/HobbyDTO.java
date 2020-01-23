package com.hackerrank.assignment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class HobbyDTO implements Serializable {

    private String id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name is not more then 100 char")
    private String name;
}
