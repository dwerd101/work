package com.example.controller;

import com.example.dao.ProfileResultService;
import com.example.dto.ProfileMapper;
import com.example.dto.ProfileResultDto;
import com.example.model.*;
import com.example.repository.ProfileResultRep;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Controller {

    @Autowired
    private ProfileResultService profileResultService;
    private Sources sources;
    private Owners owners;
    private Tables tables;
    private Field field;

    {
        sources = new Sources();
        owners = new Owners();
        tables = new Tables();
        field = new Field();
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<ProfileResultDto>> getProfileResult(@PathVariable("id") Long standId,
                                                            @RequestParam(required = false, defaultValue = "0") int page,
                                                            @RequestParam(required = false, defaultValue = "20") int size) {
        if (size == 0) {
            return new ResponseEntity<>(  HttpStatus.NO_CONTENT);
        } else {
            Pageable pageable = PageRequest.of(page, size);
            Page<ProfileResult> profileResults = profileResultService.findById(standId, pageable);
            Page<ProfileResultDto> pageProfileResultDto = profileResults.map(profileResult -> {
                sources.setName(profileResult.getFieldId().getTableId().getOwnerId().getSourceId().getName());
                owners.setName(profileResult.getFieldId().getTableId().getOwnerId().getName());
                tables.setName(profileResult.getFieldId().getTableId().getName());
                field.setFieldName(profileResult.getFieldId().getFieldName());
                ProfileResultDto profileResultDto = ProfileMapper.INSTANCE.profileResultDto(profileResult,
                        sources, owners, tables, field);
                return profileResultDto;
            });
            return new ResponseEntity<>(pageProfileResultDto, HttpStatus.OK);
        }
    }
}
