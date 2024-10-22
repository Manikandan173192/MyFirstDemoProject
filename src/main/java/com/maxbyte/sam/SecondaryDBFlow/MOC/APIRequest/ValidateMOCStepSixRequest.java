package com.maxbyte.sam.SecondaryDBFlow.MOC.APIRequest;

import com.maxbyte.sam.SecondaryDBFlow.MOC.Entity.MOCStepSixAL;
import lombok.Data;

import java.util.List;

@Data
public class ValidateMOCStepSixRequest {

    private String mocNumber;

    MOCStepSixAL approverList;
}
