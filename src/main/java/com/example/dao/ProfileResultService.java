package com.example.dao;

import com.example.model.ProfileResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;



public interface ProfileResultService {
    Page<ProfileResult> findById(Long id, Pageable pageable);
}
