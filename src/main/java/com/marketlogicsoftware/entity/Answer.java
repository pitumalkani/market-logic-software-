package com.marketlogicsoftware.entity;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * Instantiates a new answer.
 */
@Data
public class Answer {

    /** The option. */
    @NotNull
    private String option;
    
    /** The count. */
    @JsonIgnore
    private int count = 0;
    
    /** The percentage. */
    private float percentage;
    
}
