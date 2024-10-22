package com.maxbyte.sam.SecondaryDBFlow.MOC.APIRequest;

import com.maxbyte.sam.SecondaryDBFlow.MOC.Entity.MOCStepSixAL;
import lombok.Data;

import java.util.List;

@Data
public class AddMOCStepSixRequest {

    private String mocNumber;

    List<MOCStepSixAL> approverList;
}
