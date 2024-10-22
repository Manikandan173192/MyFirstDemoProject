package com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;



@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer organizationId;
    private String organizationCode;
    private String organizationDescription;
    private Boolean isActive;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;



}
