package com.example.controller;

import com.example.dao.ProfileResultService;
import com.example.dto.ProfileResultDto;


import com.example.model.ProfileResult;
import com.example.model.ProfileResultView;
import com.example.repository.ProfileResultDao;
import com.example.repository.ProfileResultViewRep;
import com.example.specification.*;
import com.google.common.base.Joiner;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
public class Controller {

    @Autowired
    private ProfileResultDao services;

    private ProfileResultService profileResultService;
    @Autowired
    private ProfileResultViewRep resultViewRep;

    public Controller(ProfileResultService profileResultService) {
        this.profileResultService = profileResultService;
    }

    @GetMapping(value = "source/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<ProfileResultDto>> getProfileResult(@PathVariable("id") Long standId,
                                                            @RequestParam(required = false, defaultValue = "0") int page,
                                                            @RequestParam(required = false, defaultValue = "20") int size) {

        if (size == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            Pageable pageable = PageRequest.of(page, size);
            //Hibernate
            // Page<ProfileResultDto> profileResults = profileResultService.findBySourceIdHibernate(standId,pageable);
            //JDBC
            Page<ProfileResultDto> profileResults = profileResultService.findBySourceIdJdbcTemplate(standId, pageable);
            return new ResponseEntity<>(profileResults, HttpStatus.OK);
        }
    }

    @PutMapping(value = "s/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ProfileResultDto> changeProfileResult(@PathVariable("id") Long standId,
                                               @RequestBody List<ProfileResultDto> profileResultDto) {
        List<ProfileResultDto> profileResultDtoList = profileResultService.findByIdAndProfileId(standId, profileResultDto);
        for (int i = 0; i < profileResultDto.size(); i++) {
            ProfileResultDto profileResult = profileResultDtoList.get(i);
            ProfileResultDto newProfileResult = profileResultDto.get(i);
            profileResult.setComment(newProfileResult.getComment());
            profileResultDtoList.set(i, profileResult);
        }
        return profileResultService.saveProfileResult(profileResultDtoList);
    }

    @GetMapping("source")
   public List<ProfileResultView> search(@RequestParam(value = "search") String search) {


        List<SearchCriteria2> params = new ArrayList<>();
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)([:<>])(\\w+?|.*?),", Pattern.UNICODE_CHARACTER_CLASS);
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                params.add(new SearchCriteria2(matcher.group(1), matcher.group(2), matcher.group(3)));
            }
        }
        return services.searchProfile(params);


  // /ssource/id

        // Отдааю серверу питон Затем получаю ти отправлюя к  себеб

      /*  ProfileResultSpecificationBuilder2 builder = new ProfileResultSpecificationBuilder2();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?|.*?),", Pattern.UNICODE_CHARACTER_CLASS);
      //  if(!search.contains(".")) {
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }*/
        //}
       /* else{
        //    String text = search.replaceAll(".*?search=","");
            pattern = Pattern.compile("(\\w+?)(|:|<|>|).*?[\\w .](\\w+?),", Pattern.UNICODE_CHARACTER_CLASS);
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }*/
    /*    BooleanExpression spec = builder.build();
        return  resultViewRep.findAll(spec);*/

      /* ProfileResultSpecificationsBuilder builder = new ProfileResultSpecificationsBuilder();
        String operationSetExper = Joiner.on("|")
                .join(SearchOperation.SIMPLE_OPERATION_SET);
        Pattern pattern = Pattern.compile("(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),",
                Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(4), matcher.group(3), matcher.group(5));
        }

        Specification<ProfileResultView> spec = builder.build();
        return profileResultService.findAll(spec);*/
    }
}
    //Фильтры
    /*Тест изменить одну запись, несколько.

    @GetMapping("")
*/

