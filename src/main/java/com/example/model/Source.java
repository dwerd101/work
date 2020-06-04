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
@javax.persistence.Table(name = "source")
public class Source {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
 //   @JsonProperty("id")
    private long id;

    @Column(name = "name")
  //  @JsonProperty("name")
    private String name;

    @OneToMany(mappedBy = "sourceId", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude  private Set<Owner> owners;

}
