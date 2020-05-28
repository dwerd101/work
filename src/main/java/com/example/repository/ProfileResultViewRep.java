package com.example.repository;

import com.example.model.ProfileResultView;
import com.example.model.QProfileResultView;
import com.querydsl.core.types.EntityPath;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;
//import com.mysema.
@Repository
public interface ProfileResultViewRep extends JpaRepository<ProfileResultView, Long>,
        QuerydslPredicateExecutor<ProfileResultView>, QuerydslBinderCustomizer<QProfileResultView> {

    @Override
    default void customize(QuerydslBindings querydslBindings, QProfileResultView qProfileResultView) {
        querydslBindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }
}
