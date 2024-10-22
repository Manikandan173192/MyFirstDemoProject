package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkClearance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String workOrderNumber;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "workOrderNumber", referencedColumnName = "workOrderNumber")
    List<WorkClearanceChild> workClearanceList;

}
