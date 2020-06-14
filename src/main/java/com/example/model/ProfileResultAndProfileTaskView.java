package com.example.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.concurrent.Immutable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Immutable
@Table(name = "profile_result_and_task_view")
public class ProfileResultAndProfileTaskView {

    @Id
    @Column(name = "prof_id")
    private long profId;

    @Column(name = "field_id")
    private long fieldId;
    @Column(name = "date_field")
    private LocalDate dateField;
    @Column(name = "domain")
    private String domain;
    @Column(name = "comment")
    private String comment;
    @Column(name = "profile_task_id")
    private long profileTaskId;
    @Column(name = "source_id")
    private long sourceId;
    @Column(name = "create_date")
    private LocalDate createDate;
    @Column(name = "update_date")
    private LocalDate updateDate;
    @Column(name = "status")
    private String status;

}
