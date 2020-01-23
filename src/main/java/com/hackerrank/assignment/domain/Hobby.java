package com.hackerrank.assignment.domain;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * The persistent class for the Hobby database table.
 *
 * @author mukhtiar.ahmed
 * @version 1.0
 */
@Data
@Entity(name = "hobby")
public class Hobby extends BaseEntity {

    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

}
