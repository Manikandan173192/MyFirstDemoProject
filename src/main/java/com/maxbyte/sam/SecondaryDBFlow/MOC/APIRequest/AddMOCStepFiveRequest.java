package com.maxbyte.sam.SecondaryDBFlow.MOC.APIRequest;


import com.maxbyte.sam.SecondaryDBFlow.MOC.Entity.MOCStepFiveDD;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddMOCStepFiveRequest {

    private String mocNumber;

    List<MOCStepFiveDD> stepFiveDDList;

}
