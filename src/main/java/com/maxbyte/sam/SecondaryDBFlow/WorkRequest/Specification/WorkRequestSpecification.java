package com.maxbyte.sam.SecondaryDBFlow.WorkRequest.Specification;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Asset;
import com.maxbyte.sam.SecondaryDBFlow.Helper.GenericSpecification;
import com.maxbyte.sam.SecondaryDBFlow.Helper.SearchCriteria;

public class WorkRequestSpecification extends GenericSpecification<Asset> {
    public WorkRequestSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
