package com.example.dao;

import com.example.dto.ProfileResultDto;
import com.example.model.ProfileResult;

import com.example.model.ProfileResultView;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;


public interface ProfileResultService {
    Page<ProfileResultDto> findBySourceIdHibernate(Long id, Pageable pageable);
    Page<ProfileResultDto> findBySourceIdJdbcTemplate(Long id, Pageable pageable);
    List<ProfileResultDto> findByIdAndProfileId(Long id, List<ProfileResultDto> profileId);
    List<ProfileResultDto> saveProfileResult(List<ProfileResultDto> profileResult);
    Iterable<ProfileResultView> findAll(BooleanExpression profileResult);


}

