package com.example.repository;

import com.example.model.ProfileResultView;
import com.example.specification.SearchCriteria;

import java.util.List;


public interface ProfileResultDao {
        List<ProfileResultView> searchProfile(List<SearchCriteria> params);
        void save(ProfileResultView entity);
}
