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
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProfileResultView that = (ProfileResultView) o;

        if (profileId != that.profileId) return false;
        if (!Objects.equals(sourceName, that.sourceName)) return false;
        if (!Objects.equals(ownersName, that.ownersName)) return false;
        if (!Objects.equals(tablesName, that.tablesName)) return false;
        if (!Objects.equals(fieldName, that.fieldName)) return false;
        if (!Objects.equals(nameDomain, that.nameDomain)) return false;
        return Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        int result = (int) (profileId ^ (profileId >>> 32));
        result = 31 * result + (sourceName != null ? sourceName.hashCode() : 0);
        result = 31 * result + (ownersName != null ? ownersName.hashCode() : 0);
        result = 31 * result + (tablesName != null ? tablesName.hashCode() : 0);
        result = 31 * result + (fieldName != null ? fieldName.hashCode() : 0);
        result = 31 * result + (nameDomain != null ? nameDomain.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
