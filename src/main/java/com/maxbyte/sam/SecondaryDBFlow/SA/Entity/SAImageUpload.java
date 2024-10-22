package com.maxbyte.sam.SecondaryDBFlow.SA.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class SAImageUpload {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer imageId;
    private String UploadImage;
    private String assetNumber;

}
