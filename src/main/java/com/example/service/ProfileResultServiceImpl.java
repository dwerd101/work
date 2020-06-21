package com.example.service;

import com.example.dao.ProfileResultService;
import com.example.dto.ProfileMapper;
import com.example.dto.ProfileResultDto;
import com.example.model.ProfileResult;
import com.example.model.ProfileResultAndProfileTaskView;
import com.example.model.ProfileResultView;
import com.example.model.Source;
import com.example.repository.*;
import com.example.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileResultServiceImpl implements ProfileResultService {


    private ProfileResultRep resultRep;
    private JdbcTemplate jdbcTemplate;
    private ProfileResultDaoRep profileResultDaoRep;
    private ProfileResultAndProfileTaskRep profileResultAndProfileTaskRep;
    private ProfileResultViewRep profileResultViewRep;
    private SourceRep sourceRep;


    @Override
    public Page<ProfileResultDto> findByTaskIdHibernate(Long id, Pageable pageable) {
        Page<ProfileResultAndProfileTaskView> profileResultAndProfileTaskViewList = profileResultAndProfileTaskRep.findByProfileTaskId(id, pageable);
        int total = profileResultAndProfileTaskViewList.getTotalPages();
        List<ProfileResultAndProfileTaskView> profileTaskViews = profileResultAndProfileTaskViewList.getContent();
        List<ProfileResult> profileResultList = profileTaskViews.stream().map(profileResultAndProfileTaskView -> {
            ProfileResult profileResult = resultRep.findById(profileResultAndProfileTaskView.getProfId());
            return profileResult;
        }).collect(Collectors.toList());

        return new PageImpl<>(profileResultList.stream().map(profileResult -> new ProfileResultDto(
                profileResult.getId(),
                profileResult.getFieldId().getTableId().getOwnerId().getSourceId().getName(),
                profileResult.getFieldId().getTableId().getOwnerId().getName(),
                profileResult.getFieldId().getTableId().getName(),
                profileResult.getFieldId().getFieldName(),
                profileResult.getDomain(),
                profileResult.getComment()
        )).collect(Collectors.toList()), pageable, total);
    }


    @Override
    public Page<ProfileResultDto> findBySourceIdJdbcTemplate(Long id, Pageable pageable) {
        //language=sql
        final String FIND_BY_ID = "select * " +
                "from profile_result inner join field f on profile_result.field_id = f.id\n" +
                "                join public.table  t on f.table_id = t.id\n" +
                "                join owner o on t.owner_id = o.id\n" +
                "                join source sources on o.source_id = sources.id\n" +
                "where sources.id=?" +
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
                    String nameDomain = resultSet.getString("domain");
                    String comment = resultSet.getString("comment");
                    return new ProfileResultDto(profileId, sourceName, ownersName, tablesName, fieldName, nameDomain, comment);
                }
        );
        int total;
        if (profileResultDtoList.size() == 0) total = 0;
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

    @Autowired
    public void setProfileResultDaoRep(ProfileResultDaoRep profileResultDaoRep) {
        this.profileResultDaoRep = profileResultDaoRep;
    }

    @Autowired
    public void setProfileResultAndProfileTaskRep(ProfileResultAndProfileTaskRep profileResultAndProfileTaskRep) {
        this.profileResultAndProfileTaskRep = profileResultAndProfileTaskRep;
    }
    @Autowired
    public void setProfileResultViewRep(ProfileResultViewRep profileResultViewRep) {
        this.profileResultViewRep = profileResultViewRep;
    }

    @Autowired
    public void setSourceRep(SourceRep sourceRep) {
        this.sourceRep = sourceRep;
    }

    @Override
    public Page<ProfileResultDto> findBySourceIdHibernate(Long id, Pageable pageable) {
        Page<ProfileResult> profileResults = resultRep.findByIdAndReturnList(id, pageable);
        int total = profileResults.getTotalPages();
        return new PageImpl<>(profileResults.stream().map(profileResult -> new ProfileResultDto(
                profileResult.getId(),
                profileResult.getFieldId().getTableId().getOwnerId().getSourceId().getName(),
                profileResult.getFieldId().getTableId().getOwnerId().getName(),
                profileResult.getFieldId().getTableId().getName(),
                profileResult.getFieldId().getFieldName(),
                profileResult.getDomain(),
                profileResult.getComment()
        )).collect(Collectors.toList()), pageable, total);
    }

    @Override
    public List<ProfileResultDto> findByIdAndProfileId(Long id, List<ProfileResultDto> profileId) {

        List<ProfileResult> profileResultsList = new LinkedList<>();
        for (ProfileResultDto pr : profileId
        ) {
            ProfileResult profileResult = resultRep.findById(id, pr.getProfileId());
            profileResultsList.add(profileResult);
        }

        List<ProfileResultDto> newProfileResultDtoList = profileResultsList.stream().map(profileResult -> new ProfileResultDto(
                profileResult.getId(),
                profileResult.getFieldId().getTableId().getOwnerId().getSourceId().getName(),
                profileResult.getFieldId().getTableId().getOwnerId().getName(),
                profileResult.getFieldId().getTableId().getName(),
                profileResult.getFieldId().getFieldName(),
                profileResult.getDomain(),
                profileResult.getComment()

        )).collect(Collectors.toList());
        return newProfileResultDtoList;
    }

    @Override
    public List<ProfileResultDto> findByTaskIdAndProfileId(Long taskId, List<ProfileResultDto> profileId) {
        List<ProfileResult> profileResultsList = new LinkedList<>();
        for (ProfileResultDto pr : profileId
        ) {

            ProfileResult profileResult = resultRep.findByTaskIdAndProfileId(taskId, pr.getProfileId());
            profileResultsList.add(profileResult);
        }

        return profileResultsList.stream().map(profileResult -> new ProfileResultDto(
                profileResult.getId(),
                profileResult.getFieldId().getTableId().getOwnerId().getSourceId().getName(),
                profileResult.getFieldId().getTableId().getOwnerId().getName(),
                profileResult.getFieldId().getTableId().getName(),
                profileResult.getFieldId().getFieldName(),
                profileResult.getDomain(),
                profileResult.getComment()

        )).collect(Collectors.toList());
    }

    @Override
    public List<ProfileResultDto> saveProfileResult(List<ProfileResultDto> profileResultList) {
        List<ProfileResult> profileResultEntityList = profileResultList.stream().map(profileResultDto -> {
            ProfileResult profileResult = resultRep.findById(profileResultDto.getProfileId());
            return ProfileMapper.INSTANCE.profileResult(profileResultDto, profileResult);

        }).collect(Collectors.toList());
        resultRep.saveAll(profileResultEntityList);
        return profileResultList;
    }

    @Override
    public List<ProfileResultView> searchProfile(List<SearchCriteria> params) {
        return profileResultDaoRep.searchProfile(params);
    }

    @Override
    public List<ProfileResultDto> findByIdSource(Long id) {
        List<ProfileResult> profiles = resultRep.findByIdSource(id);
        return profiles.stream().map(profileResult -> new ProfileResultDto(
                profileResult.getId(),
                profileResult.getFieldId().getTableId().getOwnerId().getSourceId().getName(),
                profileResult.getFieldId().getTableId().getOwnerId().getName(),
                profileResult.getFieldId().getTableId().getName(),
                profileResult.getFieldId().getFieldName(),
                profileResult.getDomain(),
                profileResult.getComment()
        )).collect(Collectors.toList());
    }

    @Override
    public List<ProfileResultView> findAll() {
        return profileResultViewRep.findAll();

    }

    @Override
    public List<ProfileResultView> findAllById(Long id) {
        Optional<Source> sourceName = sourceRep.findById(id);
        String sourceNameText  ;
        if(sourceName.isPresent()) sourceNameText = sourceName.get().getName();
        else return null;
        return profileResultViewRep.findAllBySourceName(sourceNameText);
    }

    @Override
    public void saveProfileResultAll(List<ProfileResultView> profileResultViewList) {
        List<ProfileResultDto> listConvert = profileResultViewList.stream().map(profileResultView ->
                new ProfileResultDto(profileResultView.getProfileId(), profileResultView.getSourceName(),
                        profileResultView.getOwnersName(), profileResultView.getTablesName(),
                        profileResultView.getFieldName(), profileResultView.getNameDomain(),
                        profileResultView.getComment())).collect(Collectors.toList());
        List<ProfileResult> profileResultEntityList = listConvert.stream().map(profileResultDto -> {
            ProfileResult profileResult = resultRep.findById(profileResultDto.getProfileId());
            profileResult.setComment(profileResultDto.getComment());
            profileResultDto.setNameDomain(profileResult.getDomain());
            return ProfileMapper.INSTANCE.profileResult(profileResultDto, profileResult);

        }).collect(Collectors.toList());
        resultRep.saveAll(profileResultEntityList);

    }
}