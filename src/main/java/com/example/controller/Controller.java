package com.example.controller;

import com.example.dao.ProfileResultService;
import com.example.dto.ProfileMapper;
import com.example.dto.ProfileResultDto;
import com.example.model.*;

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

    private ProfileResultService profileResultService;


    public Controller(ProfileResultService profileResultService) {
        this.profileResultService = profileResultService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<ProfileResultDto>> getProfileResult(@PathVariable("id") Long standId,
                                                            @RequestParam(required = false, defaultValue = "0") int page,
                                                            @RequestParam(required = false, defaultValue = "20") int size) {

        if (size == 0) {
            return new ResponseEntity<>(  HttpStatus.NO_CONTENT);
        } else {
            Pageable pageable = PageRequest.of(page, size);
            //Hibernate
          //  Page<ProfileResultDto> profileResults = profileResultService.findBySourceIdHibernate(standId,pageable);
            //JDBC
            Page<ProfileResultDto> profileResults = profileResultService.findBySourceIdJdbcTemplate(standId,pageable);
            return new ResponseEntity<>(profileResults, HttpStatus.OK);
        }
    }
}
