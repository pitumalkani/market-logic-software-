package com.marketlogicsoftware.entity;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@SuppressWarnings ( "deprecation" )
@Data
public class QuestionRequestDTO {

    @NotNull
    private String question;
    
    @SuppressWarnings ( "deprecation" )
    @NotNull
    @Valid
    @NotEmpty
    private Set<Answer> answers;

}
