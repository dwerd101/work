package com.example.dao;

import com.example.dto.ProfileResultDto;
import com.example.model.ProfileResult;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;


public interface ProfileResultService {
    Page<ProfileResultDto> findBySourceIdHibernate(Long id, Pageable pageable);
    Page<ProfileResultDto> findBySourceIdJdbcTemplate(Long id, Pageable pageable);
    List<ProfileResultDto> findByIdAndProfileId(Long id, Long profileId);
    List<ProfileResultDto> saveProfileResult(List<ProfileResultDto> profileResult);


}

