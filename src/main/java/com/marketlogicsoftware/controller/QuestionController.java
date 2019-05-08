package com.marketlogicsoftware.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marketlogicsoftware.entity.QuestionDTO;
import com.marketlogicsoftware.entity.QuestionRequestDTO;
import com.marketlogicsoftware.entity.QuestionResponseDTO;
import com.marketlogicsoftware.exception.PollingBackendException;
import com.marketlogicsoftware.service.ContentService;

@RestController
@RequestMapping ( value = "/question" )
public class QuestionController {

    @Autowired
    private ContentService contentService;

    @RequestMapping ( method = RequestMethod.POST )
    public QuestionResponseDTO post( @RequestBody @Valid QuestionRequestDTO question ) {
        return contentService.add( question );
    }

    @RequestMapping ( value = "/all",method = RequestMethod.POST )
    public List<QuestionResponseDTO> addSurvey( @RequestBody @Valid List<QuestionRequestDTO> questionList ) {
        return contentService.addAll( questionList );
    }

    @RequestMapping ( method = RequestMethod.GET, produces = "application/json" )
    public List<QuestionDTO> getAll() {
        return contentService.getAll();
    }

    @RequestMapping ( value = "/{id}", method = RequestMethod.GET, produces = "application/json" )
    public QuestionResponseDTO getQuestionById( @PathVariable ( "id" ) UUID id ) throws PollingBackendException {
        return contentService.getQuestionById( id );
    }

    @RequestMapping ( value = "/{id}/distribution", method = RequestMethod.GET, produces = "application/json" )
    public QuestionResponseDTO getQuestionByIdDistribution( @PathVariable ( "id" ) UUID id ) throws PollingBackendException {
        return contentService.getDistributionQuestionById( id );
    }

    @RequestMapping ( value = "/{id}", method = RequestMethod.PUT, produces = "application/json" )
    public QuestionResponseDTO update( @PathVariable ( "id" ) UUID id, @RequestBody @Valid QuestionResponseDTO questionDto ) throws PollingBackendException {
        return contentService.update( id, questionDto );
    }

    @RequestMapping ( value = "{id}", method = RequestMethod.DELETE, produces = "application/json" )
    @ResponseStatus ( HttpStatus.NO_CONTENT )
    public void delete( @PathVariable ( "id" ) UUID id ) throws PollingBackendException {
        contentService.delete( id );
    }
}
