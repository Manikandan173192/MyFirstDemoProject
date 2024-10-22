package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkClearanceChild {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String workOrderNumber;
    private String workClearance;
    private LocalDateTime validFrom;
    private LocalDateTime validTill;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
    private String uploadAttachment;
    private LocalDateTime createdOn;

}
