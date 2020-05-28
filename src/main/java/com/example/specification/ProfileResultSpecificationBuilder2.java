package com.example.specification;

import com.example.model.ProfileResult;
import com.example.model.ProfileResultView;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.data.jpa.domain.Specification;



import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProfileResultSpecificationBuilder2 {
    private final List<SearchCriteria2> params;

    public ProfileResultSpecificationBuilder2() {
        params = new ArrayList<>();
    }

    public ProfileResultSpecificationBuilder2 with(String key, String operation, Object value) {
         params.add(new SearchCriteria2(key, operation, value));
        return this;
    }

    public BooleanExpression build() {
        if (params.size() == 0) {
            return null;
        }
        final List<BooleanExpression> predicates = params.stream().map(param -> {
            ProfileResultPredicate predicate = new ProfileResultPredicate(param);
            return predicate.getPredicate();
        }).filter(Objects::nonNull).collect(Collectors.toList());

        BooleanExpression result = Expressions.asBoolean(true).isTrue();
        for (BooleanExpression predicate : predicates) {
            result = result.and(predicate);
        }

        return result;
    }

}
