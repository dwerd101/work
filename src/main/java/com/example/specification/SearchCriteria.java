package com.example.specification;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;
}
