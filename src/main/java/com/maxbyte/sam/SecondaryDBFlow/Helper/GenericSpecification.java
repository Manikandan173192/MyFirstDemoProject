package com.maxbyte.sam.SecondaryDBFlow.Helper;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class GenericSpecification<T> implements Specification<T> {

    private SearchCriteria criteria;

    public GenericSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate
            (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getOperation().equalsIgnoreCase(">=")) {
            if (root.get(criteria.getKey()).getJavaType() == Date.class) {
                return  builder.greaterThanOrEqualTo(root.get(criteria.getKey()),(Date) criteria.getValue());
            }
            else{
                return  builder.greaterThanOrEqualTo(
                        root.<String>get(criteria.getKey()), criteria.getValue().toString());
            }
        }
        else if (criteria.getOperation().equalsIgnoreCase(">")) {
            return  builder.greaterThan(
                    root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase("==")) {
            return  builder.equal(
                    root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }

        else if (criteria.getOperation().equalsIgnoreCase("<=")) {
            if (root.get(criteria.getKey()).getJavaType() == Date.class) {
                return builder.lessThanOrEqualTo(
                        root.get(criteria.getKey()),(Date) criteria.getValue());
            }
            else{
                return  builder.lessThanOrEqualTo(
                        root.<String>get(criteria.getKey()), criteria.getValue().toString());

            }
        }
        else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThan(
                    root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return  builder.like(
                        root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            }

            else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        else if (criteria.getOperation().equalsIgnoreCase ("!:")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return  builder.notEqual(
                        root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            }

            else if(criteria.getValue()==null){
                return  builder.isNull(root.get(criteria.getKey()));
            }
            else {
                return  builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;
    }
}