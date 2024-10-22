package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class WorkPermit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String workOrderNumber;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "workOrderNumber", referencedColumnName = "workOrderNumber")
    List<WorkPermitChild> workPermitList;
}
