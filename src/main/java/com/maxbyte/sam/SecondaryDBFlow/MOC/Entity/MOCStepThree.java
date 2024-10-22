package com.maxbyte.sam.SecondaryDBFlow.MOC.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MOCStepThree {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer stepThreeId;
    private String mocNumber;
    private String attachment;
    private String url;
    private String comments;
}
