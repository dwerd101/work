package com.example.dao;

import com.example.dto.ProfileResultDto;

import com.example.model.ProfileResultView;
import com.example.specification.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ProfileResultService {
    Page<ProfileResultDto> findBySourceIdHibernate(Long id, Pageable pageable);
    Page<ProfileResultDto> findBySourceIdJdbcTemplate(Long id, Pageable pageable);
    List<ProfileResultDto> findByIdAndProfileId(Long id, List<ProfileResultDto> profileId);
    List<ProfileResultDto> saveProfileResult(List<ProfileResultDto> profileResult);
    List<ProfileResultView> searchProfile(List<SearchCriteria> params);


}

