package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Set;


@Data
@AllArgsConstructor
@Table(name = "field")
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonProperty("id")
    private int id;

    @Column(name = "field_name")
    @JsonProperty("field_name")
    private String fieldName;

    @Column(name = "size")
    @JsonProperty("size")
    private String size;

    @Column(name = "type")
    @JsonProperty("type")
    private String type;

    @ManyToOne
    @JoinColumn(name = "table_id", referencedColumnName = "id")
    @JsonProperty("table_id")
    private Table tableId;

    @OneToMany(mappedBy = "fieldId", fetch = FetchType.LAZY)
    Set<ProfileResult> profileResults;


}
