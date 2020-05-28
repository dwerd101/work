package com.example.repository;

import com.example.model.ProfileResultView;
import com.example.specification.SearchCriteria2;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ProfileResultDao {

        List<ProfileResultView> searchProfile(List<SearchCriteria2> params);

        void save(ProfileResultView entity);

}
