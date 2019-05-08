package com.marketlogicsoftware.entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Instantiates a new question response DTO.
 */
@Data

/* (non-Javadoc)
 * @see com.marketlogicsoftware.entity.QuestionRequestDTO#hashCode()
 */
@EqualsAndHashCode(callSuper=true)
public class QuestionResponseDTO extends QuestionRequestDTO {

    /** The id. */
    @ApiModelProperty ( value = "Unique identifier for question" )
    @EqualsAndHashCode.Exclude
    private UUID id = UUID.randomUUID();

    /** The total count. */
    @JsonIgnore
    private int totalCount = 0;
   
    /**
     * Gets the id.
     *
     * @return the id
     */
    public UUID getId() {
        return id;
    }
    
}
