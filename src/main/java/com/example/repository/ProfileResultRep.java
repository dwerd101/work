package com.example.repository;

import com.example.dto.ProfileResultDto;
import com.example.model.ProfileResult;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("profileRep")
public interface ProfileResultRep extends JpaRepository<ProfileResult, Long> {
    @Query( nativeQuery = true,
            value = "select *" +
                    "from profile_result inner join field f on profile_result.field_id = f.id\n" +
                    "    join tables t on f.tables_id = t.id\n" +
                    "    join owners o on t.owner_id = o.id\n" +
                    "    join sources s on o.source_id = s.id\n" +
                    "where s.id=?1",
    countQuery = "select COUNT(1)" +
            "from profile_result inner join field f on profile_result.field_id = f.id\n" +
            "    join tables t on f.tables_id = t.id\n" +
            "    join owners o on t.owner_id = o.id\n" +
            "    join sources s on o.source_id = s.id\n" +
            "where s.id=?1")
    Page<ProfileResult> findByIdAndReturnList(Long id, Pageable pageable);

    @Query(nativeQuery = true, value = "select  profile_result.id,  profile_result.field_id," +
            " profile_result.date_field, " +
            "profile_result.domain, profile_result.comment\n" +
            "from profile_result inner join field f on profile_result.field_id = f.id\n" +
            "                    join tables t on f.tables_id = t.id\n" +
            "                    join owners o on t.owner_id = o.id\n" +
            "                    join sources sources on o.source_id = sources.id\n" +
            "where sources.id=?1 and profile_result.id=?2\n")
    List<ProfileResult> findByIdAndReturnList(Long id, Long profileId);

    @Query(nativeQuery = true, value = "select  profile_result.id,  profile_result.field_id," +
            " profile_result.date_field, " +
            "profile_result.domain, profile_result.comment\n" +
            "from profile_result inner join field f on profile_result.field_id = f.id\n" +
            "                    join tables t on f.tables_id = t.id\n" +
            "                    join owners o on t.owner_id = o.id\n" +
            "                    join sources sources on o.source_id = sources.id\n" +
            "where sources.id=?1 and profile_result.id=?2\n")
    ProfileResult findById(long id, long profileId);

    ProfileResult findById(long id);




}
