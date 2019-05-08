package com.marketlogicsoftware.entity;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class Answer {

    @NotNull
    private String option;
    
    @JsonIgnore
    private int count = 0;
    
    private float percentage;
    
}
