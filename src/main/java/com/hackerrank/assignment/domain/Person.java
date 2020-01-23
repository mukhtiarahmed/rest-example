package com.hackerrank.assignment.domain;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The persistent class for the Person database table.
 *
 * @author mukhtiar.ahmed
 * @version 1.0
 */
@Data
@Entity(name = "person")
public class Person extends BaseEntity {

    private static final long serialVersionUID = 9052908123617887381L;


    @NotNull
    @Column(name = "first_name", length = 25, nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", length = 25, nullable = false)
    private String lastName;


    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "colour_id", nullable = false)
    private Colour favouriteColour;


    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.LAZY)
    @JoinTable(name = "person_hobby",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "hobby_id")
    )
    private List<Hobby> hobbies = new ArrayList<>();


}
