package com.example.service;

import com.example.dao.ProfileResultService;
import com.example.dto.ProfileMapper;
import com.example.dto.ProfileResultDto;
import com.example.model.ProfileResult;
import com.example.model.ProfileResultView;
import com.example.repository.ProfileResultDaoRep;
import com.example.repository.ProfileResultRep;
import com.example.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileResultServiceImpl implements ProfileResultService {


    private ProfileResultRep resultRep;
    private JdbcTemplate jdbcTemplate;
    private ProfileResultDaoRep profileResultDaoRep;



    @Override
    public Page<ProfileResultDto> findBySourceIdJdbcTemplate(Long id, Pageable pageable) {
        //language=sql
        final String FIND_BY_ID= "select * " +
                "from profile_result inner join field f on profile_result.field_id = f.id\n" +
                "                join public.table  t on f.table_id = t.id\n" +
                "                join owner o on t.owner_id = o.id\n" +
                "                join source sources on o.source_id = sources.id\n" +
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
                    String comment = resultSet.getString("comment");
                    return new ProfileResultDto(profileId,sourceName,ownersName,tablesName,fieldName,nameDomain,comment);
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

    @Autowired
    public void setProfileResultDaoRep(ProfileResultDaoRep profileResultDaoRep) {
        this.profileResultDaoRep = profileResultDaoRep;
    }

    @Override
    public Page<ProfileResultDto> findBySourceIdHibernate(Long id, Pageable pageable) {
        Page<ProfileResult> profileResults = resultRep.findByIdAndReturnList(id,pageable);
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
    public List<ProfileResultDto> findByIdAndProfileId(Long id,  List<ProfileResultDto> profileId) {

        List<ProfileResult> profileResultsList = new LinkedList<>();
        for (ProfileResultDto pr: profileId
             ) {
            ProfileResult profileResult = resultRep.findById(id,pr.getProfileId());
            profileResultsList.add(profileResult);
        }

        List<ProfileResultDto> newProfileResultDtoList =  profileResultsList.stream().map(profileResult -> new ProfileResultDto(
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
}