package com.example.dto;

import com.example.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "ProfileResult")
public interface ProfileMapper {

    ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);
    @Mappings({
            @Mapping(target = "profileId", source = "profileResult.id"),
            @Mapping(target = "sourceName", source = "source.name"),
            @Mapping(target = "ownersName", source = "owner.name"),
            @Mapping(target = "tablesName", source = "table.name"),
            @Mapping(target = "fieldName", source = "field.fieldName"),
            @Mapping(target = "nameDomain", source = "profileResult.domain")
    })
    ProfileResultDto profileResultDto(ProfileResult profileResult, Source source, Owner owner, Table table, Field field);

    @Mappings({
            @Mapping( target = "id", source = "profileResultDto.profileId"),
            @Mapping(target = "fieldId",source = "profileResult.fieldId" ),
            @Mapping(target = "dateField",source = "profileResult.dateField" ),
            @Mapping(target = "domain",source = "profileResultDto.nameDomain" ),
            @Mapping(  target = "comment", source = "profileResultDto.comment"),
            @Mapping(target = "profileTaskId", source = "profileResult.profileTaskId")
    }
    )
    ProfileResult profileResult(ProfileResultDto profileResultDto, ProfileResult profileResult);

    @Mappings({
            @Mapping( target = "id", source = "profileResultView.profileId"),
            @Mapping(target = "fieldId",source = "profileResult.fieldId" ),
            @Mapping(target = "dateField",source = "profileResult.dateField" ),
            @Mapping(target = "domain",source = "profileResultView.nameDomain" ),
            @Mapping(  target = "comment", source = "profileResultView.comment"),
            @Mapping(target = "profileTaskId", source = "profileResult.profileTaskId")
    }
    )
    ProfileResult profileResult(ProfileResultView profileResultView, ProfileResult profileResult);
}


