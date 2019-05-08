package com.marketlogicsoftware.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marketlogicsoftware.entity.QuestionResponseDTO;
import com.marketlogicsoftware.service.ContentService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The Class SurveyController.
 */
@RestController
@RequestMapping ( value = "/survey" )
public class SurveyController {

    /** The content service. */
    @Autowired
    private ContentService contentService;
    

    /**
     * Gets the content.
     *
     * @return the content
     */
    @RequestMapping ( method = RequestMethod.GET, produces = "application/json" )
    @ApiOperation ( value = "Get the questionnaire for survey", notes = "Get the questions for an survey.", responseContainer = "List" )
    public List<QuestionResponseDTO> getContent() {
        return contentService.getSurveyContent();
    }
    
    /**
     * Save.
     *
     * @param question the question
     */
    @RequestMapping ( method = RequestMethod.POST )
    @ResponseStatus ( HttpStatus.CREATED )
    @ApiOperation ( value = "Save the response of survey", notes = "Save the response of a survey questionnaire" )
    @ApiResponses ( value = {@ApiResponse ( code = 400, message = "Bad Request" )} )
    public void save( @RequestBody @Valid List<QuestionResponseDTO> question ) {
         contentService.saveSurveyContent( question );
    }
}
