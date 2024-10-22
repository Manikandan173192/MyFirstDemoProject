package com.maxbyte.sam.SecondaryDBFlow.CWF.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Entity
public class WorkFlowConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer workFlowConfigId;
    private String workFlowNumber;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "workFlowNumber", referencedColumnName = "workFlowNumber")
    private List<WorkFlowConfiguration> workFlowConfigurations;
}
