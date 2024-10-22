package com.maxbyte.sam.SecondaryDBFlow.Configuration.Specification;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Organization;
import com.maxbyte.sam.SecondaryDBFlow.Helper.GenericSpecification;
import com.maxbyte.sam.SecondaryDBFlow.Helper.SearchCriteria;

public class OrganizationSpecification extends GenericSpecification<Organization> {
    public OrganizationSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
