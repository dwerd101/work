package com.example.service;

import com.example.dao.SourceService;
import com.example.model.Source;
import com.example.repository.SourceRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SourceServiceImpl implements SourceService {

  private SourceRep sourceRep;

    @Autowired
    public void setSourceRep(SourceRep sourceRep) {
        this.sourceRep = sourceRep;
    }

    @Override
    public List<Source> findAll() {
        return sourceRep.findAll();
    }
}
