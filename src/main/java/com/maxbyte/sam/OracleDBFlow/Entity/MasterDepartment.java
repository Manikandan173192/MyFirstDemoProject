package com.maxbyte.sam.OracleDBFlow.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class MasterDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name ="DEPARTMENT_ID")
    private Integer departmentId;
    private Integer ORGANIZATION_ID;
    private String ORGANIZATION_CODE;
    private String DEPARTMENT_CODE;
    private String DESCRIPTION;
    private Integer LOCATION_ID;
    private String LOCATION_CODE;
    private String LOCATION_DESCRIPTION;
    private String MAINT_COST_CATEGORY_VALUE;
}
