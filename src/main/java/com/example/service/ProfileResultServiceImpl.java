package com.example.service;

import com.example.dao.ProfileResultService;
import com.example.dto.ProfileResultDto;
import com.example.model.ProfileResult;
import com.example.repository.ProfileResultRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileResultServiceImpl implements ProfileResultService {


    private ProfileResultRep resultRep;
    private JdbcTemplate jdbcTemplate;


    @Override
    public Page<ProfileResultDto> findBySourceIdJdbcTemplate(Long id, Pageable pageable) {
        //language=sql
        final String FIND_BY_ID= "select  profile_result.id, sources.name, o.name, t.name, f.field_name, " +
                "profile_result.domain\n" +
                "from profile_result inner join field f on profile_result.field_id = f.id\n" +
                "                join tables t on f.tables_id = t.id\n" +
                "                join owners o on t.owner_id = o.id\n" +
                "                join sources sources on o.source_id = sources.id\n" +
                "where sources.id=?"+
                "LIMIT " + pageable.getPageSize() + " " +
                "OFFSET " + pageable.getOffset();



        List<ProfileResultDto> profileResultDtoList = jdbcTemplate.query(FIND_BY_ID, preparedStatement ->
                        preparedStatement.setLong(1, id),
                (resultSet, i) -> {
                    long profileId = resultSet.getLong("id");
                    String sourceName = resultSet.getString("name");
                    String ownersName = resultSet.getString(3);
                    String tablesName = resultSet.getString(4);
                    String fieldName = resultSet.getString("field_name");
                    String nameDomain = resultSet.getString("domain") ;
                    return new ProfileResultDto(profileId,sourceName,ownersName,tablesName,fieldName,nameDomain);
                }
        );
        int total;
        if(profileResultDtoList.size()==0)  total = 0;
        else total = profileResultDtoList.size();
        return new PageImpl<>(profileResultDtoList, pageable, total);
    }

    @Resource(name = "profileRep")
    public void setResultRep(ProfileResultRep resultRep) {
        this.resultRep = resultRep;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Page<ProfileResultDto> findBySourceIdHibernate(Long id, Pageable pageable) {
        Page<ProfileResult> profileResults = resultRep.findById(id,pageable);
        int total = profileResults.getTotalPages();
        return new PageImpl<>(profileResults.stream().map(profileResult -> new ProfileResultDto(
                profileResult.getId(),
                profileResult.getFieldId().getTableId().getOwnerId().getSourceId().getName(),
                profileResult.getFieldId().getTableId().getOwnerId().getName(),
                profileResult.getFieldId().getTableId().getName(),
                profileResult.getFieldId().getFieldName(),
                profileResult.getDomain()
        )).collect(Collectors.toList()), pageable, total);
    }

}