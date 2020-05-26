package com.example.specification;

import com.example.model.ProfileResult;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProfileResultSpecificationsBuilder {
    private final List<SearchCriteria> params;

    public ProfileResultSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    // API

    public final ProfileResultSpecificationsBuilder with(final String key, final String operation, final Object value, final String prefix, final String suffix) {
        return with(null, key, operation, value, prefix, suffix);
    }

    public final ProfileResultSpecificationsBuilder with(final String orPredicate, final String key, final String operation, final Object value, final String prefix, final String suffix) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (op != null) {
            if (op == SearchOperation.EQUALITY) { // the operation may be complex operation
                final boolean startWithAsterisk = prefix != null && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
                final boolean endWithAsterisk = suffix != null && suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);

                if (startWithAsterisk && endWithAsterisk) {
                    op = SearchOperation.CONTAINS;
                } else if (startWithAsterisk) {
                    op = SearchOperation.ENDS_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation.STARTS_WITH;
                }
            }
            params.add(new SearchCriteria(orPredicate, key, op, value));
        }
        return this;
    }

    public Specification<ProfileResult> build() {
        if (params.size() == 0)
            return null;

        Specification<ProfileResult> result = new ProfileResultSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i).isOrPredicate()
                    ? Specification.where(result).or(new ProfileResultSpecification(params.get(i)))
                    : Specification.where(result).and(new ProfileResultSpecification(params.get(i)));
        }

        return result;
    }

    public final ProfileResultSpecificationsBuilder with(ProfileResultSpecification spec) {
        params.add(spec.getCriteria());
        return this;
    }

    public final ProfileResultSpecificationsBuilder with(SearchCriteria criteria) {
        params.add(criteria);
        return this;
    }
}

