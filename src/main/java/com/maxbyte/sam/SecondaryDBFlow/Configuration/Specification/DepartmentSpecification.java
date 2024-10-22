package com.maxbyte.sam.SecondaryDBFlow.Configuration.Specification;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Department;
import com.maxbyte.sam.SecondaryDBFlow.Helper.GenericSpecification;
import com.maxbyte.sam.SecondaryDBFlow.Helper.SearchCriteria;

public class DepartmentSpecification extends GenericSpecification<Department> {
    public DepartmentSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
