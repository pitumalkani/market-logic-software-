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
import com.marketlogicsoftware.service.ContentService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The Class QuestionController.
 */
@RestController
@RequestMapping ( value = "/question" )
public class QuestionController {

    /** The content service. */
    @Autowired
    private ContentService contentService;

    /**
     * Adds the.
     *
     * @param question the question
     * @return the question response DTO
     */
    @RequestMapping ( method = RequestMethod.POST )
    @ApiOperation ( value = "Add new question to list of questions.", notes = "Lists all available questions for an survey.", response = QuestionResponseDTO.class )
    @ApiResponses ( value = {@ApiResponse ( code = 400, message = "Bad Request" )} )
    @ResponseStatus ( HttpStatus.CREATED )
    public QuestionResponseDTO add( @RequestBody @Valid QuestionRequestDTO question ) {
        return contentService.add( question );
    }

    /**
     * Adds the all.
     *
     * @param questionList the question list
     * @return the list
     */
    @RequestMapping ( value = "/all", method = RequestMethod.POST )
    @ApiOperation ( value = "Add list of question.", notes = "Lists all available questions for an survey.", response = QuestionResponseDTO.class )
    @ResponseStatus ( HttpStatus.CREATED )
    public List<QuestionResponseDTO> addAll( @RequestBody @Valid List<QuestionRequestDTO> questionList ) {
        return contentService.addAll( questionList );
    }

    /**
     * Gets the all.
     *
     * @return the all
     */
    @ApiOperation ( value = "Gets list of questions.", notes = "Lists all available questions for an survey.", responseContainer = "List" )
    @RequestMapping ( method = RequestMethod.GET, produces = "application/json" )
    public List<QuestionDTO> getAll() {
        return contentService.getAll();
    }

    /**
     * Gets the question by id.
     *
     * @param id the id
     * @return the question by id
     * @throws PollingBackendException the polling backend exception
     */
    @RequestMapping ( value = "/{id}", method = RequestMethod.GET, produces = "application/json" )
    @ApiOperation ( value = "Gets one question by ID.", notes = "Gets one question by passing in the ID in a path parameter.", response = QuestionResponseDTO.class )
    @ApiResponses ( value = {@ApiResponse ( code = 200, message = "Success" ), @ApiResponse ( code = 400, message = "Bad Request" )

    } )
    public QuestionResponseDTO getQuestionById( @PathVariable ( "id" ) UUID id )  {
        return contentService.getQuestionById( id );
    }

    /**
     * Gets the question by id distribution.
     *
     * @param id the id
     * @return the question by id distribution
     * @throws PollingBackendException the polling backend exception
     */
    @RequestMapping ( value = "/{id}/distribution", method = RequestMethod.GET, produces = "application/json" )
    @ApiOperation ( value = "Gets distribution of question by ID.", notes = "Gets distribution of question by passing in the ID in a path parameter.", response = QuestionResponseDTO.class )
    @ApiResponses ( value = {@ApiResponse ( code = 200, message = "Success" ), @ApiResponse ( code = 400, message = "Bad Request" )
    } )
    public QuestionResponseDTO getQuestionByIdDistribution( @PathVariable ( "id" ) UUID id ) {
        return contentService.getDistributionQuestionById( id );
    }

    /**
     * Update.
     *
     * @param id the id
     * @param questionDto the question dto
     * @return the question response DTO
     */
    @RequestMapping ( value = "/{id}", method = RequestMethod.PUT, produces = "application/json" )
    @ApiOperation ( value = "Update question by ID.", notes = "Updates question by passing in the ID in a path parameter.", response = QuestionResponseDTO.class )
    @ApiResponses ( value = {@ApiResponse ( code = 200, message = "Success" ), @ApiResponse ( code = 400, message = "Bad Request" )
    } )
    public QuestionResponseDTO update( @PathVariable ( "id" ) UUID id, @RequestBody @Valid QuestionResponseDTO questionDto ) {
        return contentService.update( id, questionDto );
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @RequestMapping ( value = "{id}", method = RequestMethod.DELETE, produces = "application/json" )
    @ResponseStatus ( HttpStatus.NO_CONTENT )
    @ApiOperation ( value = "Delete question by ID.", notes = "Deletes question by passing in the ID in a path parameter." )
    @ApiResponses ( value = {@ApiResponse ( code = 204, message = "No Content" ), @ApiResponse ( code = 400, message = "Bad Request" )
    } )
    public void delete( @PathVariable ( "id" ) UUID id ) {
        contentService.delete( id );
    }
}
