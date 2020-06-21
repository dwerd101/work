package com.example.repository;

import com.example.model.ProfileResultView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileResultViewRep extends JpaRepository<ProfileResultView, Long> {

    List<ProfileResultView> findAllBySourceName(String sourceName);
}
