package com.hackerrank.assignment.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;


/**
 * The persistent class for the Colour database table.
 *
 * @author mukhtiar.ahmed
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper=true)
@Entity(name = "colour")
public class Colour extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = "hex", length = 10)
    private String hex;


}
