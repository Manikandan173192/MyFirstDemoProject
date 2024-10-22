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
public class WorkPermitChild {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String workOrderNumber;
    private String workPermit;
    private String permitType;
    private String status;
    private LocalDateTime validFrom;
    private LocalDateTime validTill;
    private String description;
    private LocalDateTime createdOn;

}
