package com.marketlogicsoftware.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.marketlogicsoftware.entity.Answer;
import com.marketlogicsoftware.entity.QuestionResponseDTO;
import com.marketlogicsoftware.service.ContentService;

import io.swagger.annotations.ApiOperation;

/**
 * The Class AnswerController.
 */
@RestController
@RequestMapping ( value = "/question" )
public class AnswerController {

    /** The content service. */
    @Autowired
    private ContentService contentService;

    /**
     * Update.
     *
     * @param id the id
     * @param options the options
     * @return the question response DTO
     */
    @ApiOperation ( value = "Update a question.", notes = "Lists all available questions for an survey.", response = QuestionResponseDTO.class, responseContainer="List" )
    @RequestMapping ( value = "/{id}/answer", method = RequestMethod.PUT, produces = "application/json" )
    public QuestionResponseDTO update( @PathVariable ( "id" ) UUID id, @RequestBody List<Answer> options ) {
        return contentService.update( id, options );
    }
}
