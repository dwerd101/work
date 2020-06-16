package com.example.controller;

import com.example.dao.ProfileResultService;
import com.example.dao.SourceService;
import com.example.dto.ProfileResultDto;


import com.example.log.LoggingMethod;
import com.example.model.ProfileResultView;
import com.example.model.Source;
import com.example.specification.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
@Log4j2
public class Controller {


    private ProfileResultService profileResultService;
    private SourceService sourceService;

    public Controller(ProfileResultService profileResultService, SourceService sourceService) {
        this.profileResultService = profileResultService;
        this.sourceService = sourceService;
    }

    @GetMapping(value = "task/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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
            Page<ProfileResultDto> profileResults = profileResultService.findByTaskIdHibernate(standId,pageable);
            return new ResponseEntity<>(profileResults, HttpStatus.OK);
        }
    }

    @LoggingMethod
    @PutMapping(value = "task/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ProfileResultDto> changeProfileResult(@PathVariable("id") Long standId,
                                               @RequestBody List<ProfileResultDto> profileResultDto) {
        List<ProfileResultDto> profileResultDtoList = profileResultService.findByTaskIdAndProfileId(standId, profileResultDto);
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
        List<SearchCriteria> params = new ArrayList<>();
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)([:<>])(\\w+?|.*?),", Pattern.UNICODE_CHARACTER_CLASS);
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
            }
        }
        return profileResultService.searchProfile(params);

        
    }

    @GetMapping("source/all")
    public List<Source> findSourceAll() {
        return sourceService.findAll();
    }

    @GetMapping("profileResultView/findAll")
    public List<ProfileResultView> findProfileResultViewListAll() {
       return profileResultService.findAll();
    }
    /*
    *  Переделаю метод который был во 2 приложении
    *  Изменить параметр запроса sourceid source/{id} на ProfileTaskId меняю на task/{id}
    *  s/{id} на task/{id}
    *  Добавить в ProfileResult столбец taskId и его заполнять
    *  Создать html страницу и отобразить таблицу.
    *  На странице список source
    *  ProfileResultView 2 страница.
    *
    * */
}


