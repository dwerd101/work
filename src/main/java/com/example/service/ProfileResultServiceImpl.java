package com.example.service;

import com.example.dao.ProfileResultService;
import com.example.model.ProfileResult;
import com.example.repository.ProfileResultRep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProfileResultServiceImpl implements ProfileResultService {

   private ProfileResultRep resultRep;

    @Resource(name = "profileRep")
    public void setResultRep(ProfileResultRep resultRep) {
        this.resultRep = resultRep;
    }

    @Override
    public Page<ProfileResult> findById(Long id, Pageable pageable) {
        return resultRep.findById(id, pageable) ;
    }
}