package com.example.repository;

import com.example.model.ProfileResult;
import com.example.model.ProfileResultAndProfileTaskView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileResultAndProfileTaskRep extends JpaRepository<ProfileResultAndProfileTaskView, Long> {

    @Query(nativeQuery = true,
            value = "select * \n" +
                    "from   profile_result_and_task_view\n" +
                    "where profile_task_id=?1",
            countQuery = "select count(1)\n" +
                    "from  profile_result_and_task_view\n" +
                    "where profile_task_id=?1")
    Page<ProfileResultAndProfileTaskView> findByProfileTaskId(Long id, Pageable pageable);


}
