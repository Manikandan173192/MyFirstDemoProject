package com.maxbyte.sam.SecondaryDBFlow.IB.APIRequest;

import com.maxbyte.sam.SecondaryDBFlow.IB.Entity.IBImage;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class IBSenderRequest {
    private String ibNumber;
//    private String senderType;
    private String senderName;
    private String senderDepartment;
    private LocalDateTime dateAndTime;
    private  String senderRemarks;
    private  String restoreDescription;

    private List<IBImage> imageUpload;

}
