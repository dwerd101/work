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
            @Mapping(target = "sourceName", source = "sources.name"),
            @Mapping(target = "ownersName", source = "owners.name"),
            @Mapping(target = "tablesName", source = "tables.name"),
            @Mapping(target = "fieldName", source = "field.fieldName"),
            @Mapping(target = "nameDomain", source = "profileResult.domain")
    })
    ProfileResultDto profileResultDto(ProfileResult profileResult, Sources sources, Owners owners, Tables tables, Field field);
}
