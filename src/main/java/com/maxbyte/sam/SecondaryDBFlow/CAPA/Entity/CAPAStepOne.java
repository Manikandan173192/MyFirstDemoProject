package com.maxbyte.sam.SecondaryDBFlow.CAPA.Entity;

import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepOneTeams;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class CAPAStepOne {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer stepOneId;
    private String capaNumber;
    private String rootCause;
    private String attachment;
    private String getUrl;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "capaNumber", referencedColumnName = "capaNumber")
    List<CAPAStepOneCA> caList;


}
