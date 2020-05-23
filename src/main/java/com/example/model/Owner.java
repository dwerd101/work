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

@javax.persistence.Table(name = "owners")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
 //   @JsonProperty("id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "source_id", referencedColumnName = "id")
  //  @JsonProperty("id")
    @ToString.Exclude   private Source sourceId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "ownerId", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude  Set<Table> tables;
}
