package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Table(name = "profile_result")
public class ProfileResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
  //  @JsonProperty("id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "field_id", referencedColumnName = "id")

  //  @JsonProperty("field_id")
    @ToString.Exclude    private Field fieldId;

    @Column(name = "date_field")
 //   @JsonProperty("date_field")
    private LocalDate dateField;

    @Column(name = "domain")
    private String domain;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "profile_task_id", referencedColumnName = "id")
    @ToString.Exclude
    private ProfileTask profileTaskId;

}
