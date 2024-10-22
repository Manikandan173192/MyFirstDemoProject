package com.maxbyte.sam.SecondaryDBFlow.IB.APIRequest;

import com.maxbyte.sam.SecondaryDBFlow.IB.Entity.ImageType;
import lombok.Data;

@Data
public class IBImageRequest {
    private String ibNumber;
    private String ImagePath;
    private ImageType imageType;
    private String restoreDescription;
}
