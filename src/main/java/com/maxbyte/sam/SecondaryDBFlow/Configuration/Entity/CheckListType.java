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
public class CheckListType {
    @Id
    @GeneratedValue
    private Integer checklistTypeId;
    private String checklistTypeName;
    private String checklistTypeDescription;
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
