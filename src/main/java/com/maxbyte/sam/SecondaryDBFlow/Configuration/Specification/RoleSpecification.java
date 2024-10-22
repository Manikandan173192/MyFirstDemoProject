package com.maxbyte.sam.SecondaryDBFlow.Configuration.Specification;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Role;
import com.maxbyte.sam.SecondaryDBFlow.Helper.GenericSpecification;
import com.maxbyte.sam.SecondaryDBFlow.Helper.SearchCriteria;

public class RoleSpecification extends GenericSpecification<Role> {
    public RoleSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
