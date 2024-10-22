package com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer departmentId;
    @Column(name = "DEPARTMENT_ID")
    private Integer eamDepartmentId;
    @Column(name = "DEPARTMENT_CODE")
    private String department;
    @Column(name = "DESCRIPTION")
    private String departmentDescription;

    @Column(name = "LOCATION_ID")
    private Integer locationId;
    @Column(name = "LOCATION_CODE")
    private String locationCode;
    @Column(name = "LOCATION_DESCRIPTION")
    private String locationDescription;

    @Column(name = "MAINT_COST_CATEGORY_VALUE")
    private String maintCostCategoryValue;
    @Column(name = "ORGANIZATION_ID")
    private Integer organizationId;
    @Column(name = "ORGANIZATION_CODE")
    private String organizationCode;



//    private Boolean isActive;
//    @CreationTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "created_on")
//    private LocalDateTime createdOn;
//    @UpdateTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "updated_on")
//    private LocalDateTime updatedOn;

}
