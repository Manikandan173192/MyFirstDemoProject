package com.maxbyte.sam.SecondaryDBFlow.IB.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class IBImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer imageUploadId;
    private String UploadImage;
    private ImageType imageType;
    private String ibNumber;
}
