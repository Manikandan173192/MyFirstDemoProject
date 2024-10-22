package com.maxbyte.sam.SecondaryDBFlow.CWF.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkFlow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer workFlowId;
    private String workFlowNumber;
    private String workFlowName;
    private Integer organizationId;
    private String organizationCode;
    private Integer departmentId;
    private String department;
    private String initiatorName;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on")
    private Date createdOn;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_on")
    private Date updatedOn;
    private boolean isActive;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "workFlowNumber", referencedColumnName = "workFlowNumber")
    private List<WorkFlowConfig> workFlowConfig;
    



}
