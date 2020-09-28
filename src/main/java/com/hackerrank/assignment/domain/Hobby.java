package com.hackerrank.assignment.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * The persistent class for the Hobby database table.
 *
 * @author mukhtiar.ahmed
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper=true)
@Entity(name = "hobby")
public class Hobby extends BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3432431L;
	@Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

}
