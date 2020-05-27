package com.example.specification;

import com.example.model.ProfileResult;
import com.example.model.ProfileResultView;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
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

    public Specification<ProfileResultView> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
                .map(ProfileResultSpecificationConsumer::new)
                .collect(Collectors.toList());

        Specification result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i)
                    .isOrPredicate()
                    ? Specification.where(result)
                    .or(specs.get(i))
                    : Specification.where(result)
                    .and(specs.get(i));
        }
        return result;
    }
}
