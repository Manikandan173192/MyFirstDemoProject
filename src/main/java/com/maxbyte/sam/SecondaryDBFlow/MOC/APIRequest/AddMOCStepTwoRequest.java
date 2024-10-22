package com.maxbyte.sam.SecondaryDBFlow.MOC.APIRequest;

import com.maxbyte.sam.SecondaryDBFlow.MOC.Entity.MOCStepTwoAP;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddMOCStepTwoRequest {

    private String mocNumber;

    List<MOCStepTwoAP> stepTwoAPList;

}
