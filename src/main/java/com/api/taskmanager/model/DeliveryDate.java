package com.api.taskmanager.model;

import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class DeliveryDate extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private LocalDate date;

    private String time;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false)
    private Boolean accomplished;

    @OneToOne(mappedBy = "deliveryDate", fetch = FetchType.EAGER)
    private Task task;

    @PrePersist
    private void setDefaultValues() {
        this.active = false;
        this.accomplished = false;
    }
}
