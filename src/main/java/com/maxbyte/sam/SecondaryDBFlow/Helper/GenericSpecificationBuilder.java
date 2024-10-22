package com.maxbyte.sam.SecondaryDBFlow.Helper;


import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GenericSpecificationBuilder<T> {

    private final List<SearchCriteria> params;

    public GenericSpecificationBuilder() {
        params = new ArrayList<SearchCriteria>();
    }

    public GenericSpecificationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<T> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
                .map(GenericSpecification::new)
                .collect(Collectors.toList());

        Specification result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result)
                    .and(specs.get(i));

        }
        return result;
    }

    /*public Predicate toPredicate
            (Root<org.apache.poi.ss.formula.functions.T> root, CriteriaQuery<?> query, CriteriaBuilder builder);
*/
}
