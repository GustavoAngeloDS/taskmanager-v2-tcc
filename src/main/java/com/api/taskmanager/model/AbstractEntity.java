package com.api.taskmanager.model;

import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Getter
@MappedSuperclass
public abstract class AbstractEntity {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", nullable = false, updatable = false)
    private Date createdOn;

    @PrePersist
    protected void onCreate() {
        createdOn = new Date();
    }
}
