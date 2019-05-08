package com.marketlogicsoftware.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketlogicsoftware.entity.Answer;
import com.marketlogicsoftware.entity.QuestionDTO;
import com.marketlogicsoftware.entity.QuestionRequestDTO;
import com.marketlogicsoftware.entity.QuestionResponseDTO;
import com.marketlogicsoftware.exception.PollingBackendException;
import com.marketlogicsoftware.util.Util;

/**
 * The Class ContentService.
 */
@Service
public class ContentService {

    /** The object mapper. */
    @Autowired
    private ObjectMapper objectMapper;

    /** The map of questions. */
    private Map<UUID, QuestionResponseDTO> mapOfQuestions;

    /** The all questions. */
    private List<QuestionDTO> allQuestions;

    /** The Constant HUNDRED. */
    private static final float HUNDRED = 100.0f;

    private final static Logger LOGGER = LoggerFactory.getLogger( ContentService.class );

    /**
     * Instantiates a new content service.
     */
    public ContentService() {
        mapOfQuestions = new HashMap<UUID, QuestionResponseDTO>();
        allQuestions = new ArrayList<QuestionDTO>();
    }

    /**
     * Adds the.
     *
     * @param question the question
     * @return the question response DTO
     */
    public QuestionResponseDTO add( QuestionRequestDTO question ) {
        QuestionResponseDTO query = new QuestionResponseDTO();
        query = objectMapper.convertValue( question, QuestionResponseDTO.class );
        boolean duplicate = mapOfQuestions.values().stream().filter( q -> q.getQuestion().equalsIgnoreCase( question.getQuestion() ) ).findAny().isPresent();
        if ( !duplicate ) {
            mapOfQuestions.put( query.getId(), query );
            return query;
        } else {
            LOGGER.debug( Util.DUPLICATE_QUESTION_FOUND + " {} ", query.getQuestion() );
            throw new BadRequestException( Util.DUPLICATE_QUESTION_FOUND );
        }

    }

    /**
     * Adds the.
     *
     * @param questionList the question
     * @return the question response DTO
     */
    public List<QuestionResponseDTO> addAll( List<QuestionRequestDTO> questionList ) {
        questionList.stream().forEach( question -> {
            QuestionResponseDTO query = new QuestionResponseDTO();
            query = objectMapper.convertValue( question, QuestionResponseDTO.class );
            boolean duplicate = mapOfQuestions.values().stream().filter( q -> q.getQuestion().equalsIgnoreCase( question.getQuestion() ) ).findAny().isPresent();
            if ( !duplicate ) {
                mapOfQuestions.put( query.getId(), query );
            } else {
                LOGGER.debug( Util.DUPLICATE_QUESTION_FOUND + " {} ", query.getQuestion() );
            }
        } );
        return getListOfQuestions();

    }

    /**
     * Gets the all.
     *
     * @return the all
     */
    public List<QuestionDTO> getAll() {
        LOGGER.debug( "Getting all questions" );
        mapOfQuestions.forEach( ( key, value ) -> {
            QuestionDTO dto = new QuestionDTO();
            dto.setId( key );
            dto.setQuestion( value.getQuestion() );
            allQuestions.add( dto );
        } );
        return allQuestions;
    }

    /**
     * Gets the question by id.
     *
     * @param id the id
     * @return the question by id
     * @throws PollingBackendException the polling backend exception
     */
    public QuestionResponseDTO getQuestionById( UUID id ) throws PollingBackendException {
        LOGGER.debug( "Get question by id" );
        if ( mapOfQuestions.containsKey( id ) ) {
            return mapOfQuestions.get( id );
        } else {
            LOGGER.debug( Util.INVALID_ID + " {} ", id );
            throw new PollingBackendException( Util.INVALID_ID );
        }
    }

    /**
     * Update.
     *
     * @param id the id
     * @param questionDto the model dto
     * @return the question response DTO
     * @throws PollingBackendException
     */
    public QuestionResponseDTO update( UUID id, QuestionResponseDTO questionDto ) throws PollingBackendException {
        LOGGER.debug( "Updating question" );
        if ( mapOfQuestions.containsKey( id ) ) {
            mapOfQuestions.put( id, questionDto );
        } else {
            LOGGER.debug( Util.INVALID_ID + " {} ", id );
            throw new PollingBackendException( Util.INVALID_ID );
        }
        return questionDto;
    }

    /**
     * Delete.
     *
     * @param id the id
     * @throws PollingBackendException
     */
    public void delete( UUID id ) throws PollingBackendException {
        LOGGER.debug( "Deleting question" );
        if ( mapOfQuestions.containsKey( id ) ) {
            mapOfQuestions.remove( id );
        } else {
            LOGGER.debug( Util.INVALID_ID + " {} ", id );
            throw new PollingBackendException( Util.INVALID_ID );
        }
    }

    /**
     * Update.
     *
     * @param id the id
     * @param options the options
     * @return the question response DTO
     */
    public QuestionResponseDTO update( UUID id, List<Answer> options ) {
        LOGGER.debug( "Answer of question updated" );
        QuestionResponseDTO dto = null;
        if ( mapOfQuestions.containsKey( id ) ) {
            dto = mapOfQuestions.get( id );
            dto.getAnswers().addAll( options );
        }
        return dto;
    }

    /**
     * Gets the survey content.
     *
     * @return the survey content
     */
    public List<QuestionResponseDTO> getSurveyContent() {
        LOGGER.debug( "Get survey content" );
        mapOfQuestions.values().stream().forEach( question -> {
            QuestionResponseDTO questionDto = calculateDistributionPercentage( mapOfQuestions.get( question.getId() ) );
            mapOfQuestions.put( question.getId(), questionDto );
        } );
        return getListOfQuestions();
    }

    /**
     * Save survey content.
     *
     * @param listOfQuestions the list of questions
     */
    public void saveSurveyContent( List<QuestionResponseDTO> listOfQuestions ) {
        LOGGER.debug( "Save Survey Content" );
        listOfQuestions.stream().forEach( question -> {
            if ( mapOfQuestions.containsKey( question.getId() ) ) {
                QuestionResponseDTO dto = mapOfQuestions.get( question.getId() );
                question.getAnswers().stream().forEach( q -> {
                    dto.getAnswers().stream().forEach( answer -> {
                        if ( answer.getOption().equalsIgnoreCase( q.getOption() ) ) {
                            answer.setCount( answer.getCount() + 1 );
                            dto.setTotalCount( dto.getTotalCount() + 1 );
                        }
                        mapOfQuestions.put( question.getId(), dto );
                    } );

                } );
            }
        } );
    }

    /**
     * Gets the distribution question by id.
     *
     * @param id the id
     * @return the distribution question by id
     */
    public QuestionResponseDTO getDistributionQuestionById( UUID id ) {
        LOGGER.debug( "Get question distribution by id" );
        QuestionResponseDTO questionDto = null;
        if ( mapOfQuestions.containsKey( id ) ) {
            questionDto = calculateDistributionPercentage( mapOfQuestions.get( id ) );
            mapOfQuestions.put( id, questionDto );
        } else {
            new NotFoundException( Util.INVALID_ID );
        }
        return questionDto;
    }

    /**
     * Calculate distribution percentage.
     *
     * @param questionDto the question dto
     * @return the question response DTO
     */
    private QuestionResponseDTO calculateDistributionPercentage( QuestionResponseDTO questionDto ) {
        questionDto.getAnswers().stream().forEach( answer -> {
            float percentage = ((float)answer.getCount() / questionDto.getTotalCount()) * HUNDRED;
            LOGGER.debug( "Setting distribution percentage " + questionDto.getTotalCount() + " percentage " + percentage );
            answer.setPercentage( percentage );
        } );
        return questionDto;
    }

    private List<QuestionResponseDTO> getListOfQuestions() {
        return mapOfQuestions.values().stream().collect( Collectors.toList() );
    }
}
