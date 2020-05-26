package com.example.specification;

import com.example.model.ProfileResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Component
public class ProfileResultSpecification implements Specification<ProfileResult> {

    private SearchCriteria criteria;
    @Override
    public Predicate toPredicate(Root<ProfileResult> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (criteria.getOperation().equalsIgnoreCase(">")) {

            return criteriaBuilder.greaterThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());

        } else if (criteria.getOperation().equalsIgnoreCase("<")) {

            return criteriaBuilder.lessThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());

        } else if (criteria.getOperation().equalsIgnoreCase(":")) {

            if (root.get(criteria.getKey()).getJavaType() == String.class) {

                return criteriaBuilder.like(
                        root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {

                return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());

            }
        }
        return null;
    }

    @Autowired
    public void setCriteria(SearchCriteria criteria) {
        this.criteria = criteria;
    }
}
