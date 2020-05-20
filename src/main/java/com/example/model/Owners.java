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

@Table(name = "owners")
public class Owners {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
 //   @JsonProperty("id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "source_id", referencedColumnName = "id")
  //  @JsonProperty("id")
    @ToString.Exclude   private Sources sourceId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "ownerId", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude  Set<Tables> tables;
}
