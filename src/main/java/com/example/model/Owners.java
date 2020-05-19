package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@Table(name = "owners")
public class Owners {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonProperty("id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "source_id", referencedColumnName = "id")
    @JsonProperty("id")
    private Sources sourceId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "ownerId", fetch = FetchType.LAZY)
    Set<Tables> tables;
}
