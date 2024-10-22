package com.maxbyte.sam.SecondaryDBFlow.CWF.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CWFWorkFlowConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String field;
    private boolean attachment;
    private String value;
    private String attachmentFile;
    private String documentNumber;

}
