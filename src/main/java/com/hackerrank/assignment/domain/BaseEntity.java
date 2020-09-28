/*
 * Copyright (C) 2017 Van Tibolli, All Rights Reserved.
 */
package com.hackerrank.assignment.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The super class of all entities of the persistence.
 *
 * @author mukhtiar.ahmed
 * @version 1.0
 */
@Data
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class BaseEntity implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", unique = true, length = 36)
    private String id;


    @Column(name = "is_active", nullable = false)
    private Boolean isActive = Boolean.TRUE;

    @CreatedDate
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @CreatedBy
    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @LastModifiedDate
    @Column(name = "modified_on", nullable = false)
    private LocalDateTime lastModifiedOn;

    @LastModifiedBy
    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;

    /**
     * Override the equals method.
     *
     * @param target the target
     * @return true if two entities have the same ID
     */
    @Override
    public boolean equals(Object target) {
        if (target instanceof BaseEntity) {
            BaseEntity entity = (BaseEntity) target;
            return entity.getClass() == target.getClass() && this.id != null && this.id.equals(entity.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return  id != null ? id.hashCode() : 0;
    }
}
