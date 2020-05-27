package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Subselect;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.concurrent.Immutable;
import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "profile_result_view")
@Immutable
public class ProfileResultView implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    private long profileId;

    @Column(name = "sources_name")
    private String sourceName;
    @Column(name = "owners_name")
    private String ownersName;
    @Column(name = "tables_name")
    private String tablesName;
    @Column(name = "field_name")
    private String fieldName;
    @Column(name = "domain")
    private String nameDomain;
    @Column(name = "comment")
    private String comment;

}
