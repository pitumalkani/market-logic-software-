package com.marketlogicsoftware.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.marketlogicsoftware.entity.Answer;
import com.marketlogicsoftware.entity.QuestionResponseDTO;
import com.marketlogicsoftware.service.ContentService;

@RestController
@RequestMapping ( value = "/question" )
public class AnswerController {

    @Autowired
    private ContentService contentService;
    
    @RequestMapping ( value = "/{id}/answer", method = RequestMethod.PUT, produces = "application/json" )
    public QuestionResponseDTO update( @PathVariable("id") UUID id, @RequestBody @Valid List<Answer> options) {
        return contentService.update( id, options );
    }
}
