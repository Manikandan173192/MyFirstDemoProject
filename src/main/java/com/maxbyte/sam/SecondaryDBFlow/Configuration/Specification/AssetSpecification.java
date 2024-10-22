package com.maxbyte.sam.SecondaryDBFlow.Configuration.Specification;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Asset;
import com.maxbyte.sam.SecondaryDBFlow.Helper.GenericSpecification;
import com.maxbyte.sam.SecondaryDBFlow.Helper.SearchCriteria;

public class AssetSpecification extends GenericSpecification<Asset> {
    public AssetSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
