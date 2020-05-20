package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

@Table(name = "tables")
public class Tables {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonProperty("id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
  //  @JsonProperty("id")
    @ToString.Exclude   private Owners ownerId;

    @Column(name = "name")
//    @JsonProperty("name")
    private String name;

    @OneToMany(mappedBy = "tableId", fetch = FetchType.LAZY)
 //   @JsonProperty("list")
    @JsonIgnore
    @ToString.Exclude   Set<Field> tables;
}
