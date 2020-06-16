package com.example.repository;

import com.example.model.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceRep extends JpaRepository<Source, Long> {

}
