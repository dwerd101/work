package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Table(name = "profile_result")
public class ProfileResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonProperty("id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "field_id", referencedColumnName = "id")
    @JsonProperty("field_id")
    private Field fieldId;

    @Column(name = "date_field")
    @JsonProperty("date_field")
    private LocalDate dateField;

    @Column(name = "domain")
    private String domain;
}
