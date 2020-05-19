package com.example.controller;

import com.example.model.ProfileResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
 interface ProfileResultRep extends JpaRepository<ProfileResult, Integer> {
    @Query( nativeQuery = true,
            value = "select profile_result.domain, profile_result.date_field, s.name\n" +
                    "from profile_result inner join field f on profile_result.field_id = f.id\n" +
                    "    join tables t on f.tables_id = t.id\n" +
                    "    join owners o on t.owner_id = o.id\n" +
                    "    join sources s on o.source_id = s.id\n" +
                    "where s.id=?1;")
    Page<ProfileResult> findById(Integer id, Pageable page);
}
