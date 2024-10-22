package com.maxbyte.sam.SecondaryDBFlow.MOC.APIRequest;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AddMOCStepThreeRequest {
    private String mocNumber;
    private String attachment;
    private String url;
    private String comments;
}
