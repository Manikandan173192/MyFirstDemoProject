package com.maxbyte.sam.SecondaryDBFlow.AIM.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class AIMImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer imageUploadId;
    private String UploadImage;
    private String aimNumber;
}

