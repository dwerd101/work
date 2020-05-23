package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

@javax.persistence.Table(name = "field")

public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
  //  @JsonProperty("id")
    private long id;

    @Column(name = "field_name")
   // @JsonProperty("field_name")
    private String fieldName;

    @Column(name = "size")
   // @JsonProperty("size")
    private String size;

    @Column(name = "type")
   // @JsonProperty("type")
    private String type;

    @ManyToOne
    @JoinColumn(name = "tables_id", referencedColumnName = "id")
   // @JsonProperty("table_id")
    @ToString.Exclude  private Table tableId;

    @OneToMany(mappedBy = "fieldId", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude  Set<ProfileResult> profileResults;


}
