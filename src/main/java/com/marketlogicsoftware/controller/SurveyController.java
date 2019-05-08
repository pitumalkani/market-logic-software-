package com.marketlogicsoftware.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.marketlogicsoftware.entity.QuestionResponseDTO;
import com.marketlogicsoftware.service.ContentService;

@RestController
@RequestMapping ( value = "/survey" )
public class SurveyController {

    @Autowired
    private ContentService contentService;
    

    @RequestMapping ( method = RequestMethod.GET, produces = "application/json" )
    public List<QuestionResponseDTO> getContent() {
        return contentService.getSurveyContent();
    }
    
    @RequestMapping ( method = RequestMethod.POST )
    public void save( @RequestBody @Valid List<QuestionResponseDTO> question ) {
         contentService.saveSurveyContent( question );
    }
}
