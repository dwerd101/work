package com.example.controller;

import com.example.model.ProfileResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;

@RestController
public class Controller {

    @Autowired
    private ProfileResultRep profileResultRep;

   @GetMapping("/")
    public Page<ProfileResult> getProfilesByData(@RequestParam String id, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
       Page<ProfileResult> test = profileResultRep.findById(Integer.parseInt(id), (org.springframework.data.domain.Pageable) pageable);
       return test;
    }
}
