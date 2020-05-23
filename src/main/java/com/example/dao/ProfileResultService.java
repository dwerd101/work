package com.example.dao;

import com.example.dto.ProfileResultDto;
import com.example.model.ProfileResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;



public interface ProfileResultService {
    Page<ProfileResultDto> findBySourceIdHibernate(Long id, Pageable pageable);
    Page<ProfileResultDto> findBySourceIdJdbcTemplate(Long id, Pageable pageable);

}

