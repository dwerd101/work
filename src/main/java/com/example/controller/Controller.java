package com.example.controller;

import com.example.model.Field;
import com.example.model.ProfileResult;
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

import java.util.HashMap;
import java.util.Map;


@RestController
public class Controller {

    @Autowired
    private ProfileResultRep profileResultRep;


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<ProfileResult>> getProfileResult(@PathVariable("id") Long standId,
                                                         @RequestParam(required = false,defaultValue = "0") int page,
                                                         @RequestParam(required = false,defaultValue = "20") int size)
    {
        Pageable pageable = PageRequest.of(page,size);
        Page<ProfileResult> profileResults = profileResultRep.findById(standId,pageable);
        return new ResponseEntity<>(profileResults, HttpStatus.OK);
    }

/*    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<Map<String, String>> d () {
        Map<String,String> map = new HashMap<>();
        map.put("22","321");
        map.put("2233","32151");
        map.put("221","32144");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }*/

}
