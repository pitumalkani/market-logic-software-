package com.marketlogicsoftware.entity;

import java.util.UUID;

import lombok.Data;

/**
 * Instantiates a new question DTO.
 */
@Data
public class QuestionDTO {

    /** The id. */
    private UUID id;
    /** The question. */
    private String question;
}
