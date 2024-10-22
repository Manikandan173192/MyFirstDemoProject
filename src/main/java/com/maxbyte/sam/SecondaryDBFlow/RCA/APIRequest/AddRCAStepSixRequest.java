package com.maxbyte.sam.SecondaryDBFlow.RCA.APIRequest;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepSixCause;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class AddRCAStepSixRequest {
    private String rcaNumber;
    private String rootCause;

    List<RCAStepSixCause> causeList;
}
