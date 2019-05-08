package com.marketlogicsoftware.entity;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

/**
 * The Class QuestionRequestDTO.
 */
@SuppressWarnings ( "deprecation" )

/**
 * Instantiates a new question request DTO.
 */
@Data
public class QuestionRequestDTO {

    /** The question. */
    @NotNull
    private String question;
    
    /** The answers. */
    @SuppressWarnings ( "deprecation" )
    @NotNull
    @Valid
    @NotEmpty
    private Set<Answer> answers;

}
