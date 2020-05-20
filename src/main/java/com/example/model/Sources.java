package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.Set;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sources")
public class Sources {

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
    @ToString.Exclude  private Set<Owners> owners;

}
