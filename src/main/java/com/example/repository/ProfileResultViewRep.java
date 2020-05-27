package com.example.repository;

import com.example.model.ProfileResultView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileResultViewRep extends JpaRepository<ProfileResultView, Long>,
        JpaSpecificationExecutor<ProfileResultView> {

}
