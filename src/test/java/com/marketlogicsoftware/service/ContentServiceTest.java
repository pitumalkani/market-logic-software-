package com.marketlogicsoftware.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketlogicsoftware.entity.Answer;
import com.marketlogicsoftware.entity.QuestionRequestDTO;
import com.marketlogicsoftware.entity.QuestionResponseDTO;

@SuppressWarnings ( "deprecation" )
@RunWith ( MockitoJUnitRunner.class )
public class ContentServiceTest {

    @InjectMocks
    ContentService contentService;

    @Spy
    private ObjectMapper objectMapper;

    /** The Constant JSON_BASE_PATH. */
    private static final String JSON_BASE_PATH = "/JSON/";

    private static final String FILE_LIST_OF_QUESTIONS = "ListOfQuestions.json";

    private static final String SURVEY_CONTENT = "SurveyResponse.json";

    private List<QuestionRequestDTO> listOfQuestions;

    private List<QuestionResponseDTO> listOfQuestionsResponse;

    @Before
    public void setUp() throws JsonParseException, JsonMappingException, IOException, URISyntaxException {
        listOfQuestions = objectMapper.readValue( returnJsonAsString( FILE_LIST_OF_QUESTIONS ), new TypeReference<List<QuestionRequestDTO>>() {
        } );

        listOfQuestionsResponse = objectMapper.readValue( returnJsonAsString( SURVEY_CONTENT ), new TypeReference<List<QuestionResponseDTO>>() {
        } );

    }

    @Test
    public void testAdd() {
        QuestionResponseDTO dto = contentService.add( listOfQuestions.get( 0 ) );
        Assert.assertNotNull( dto );

    }

    @Test ( expected = BadRequestException.class )
    public void testAddShouldThrowDuplicateQuestionException() {
        contentService.add( listOfQuestions.get( 0 ) );
        contentService.add( listOfQuestions.get( 0 ) );
    }

    @Test
    public void testAddAll() {
        contentService.addAll( listOfQuestions );
    }

    @Test
    public void testGetAll() {
        contentService.addAll( listOfQuestions );
        Assert.assertEquals( contentService.getAll().size(), listOfQuestions.size() );
    }

    @Test
    public void testGetById()  {
        QuestionResponseDTO dto = contentService.add( listOfQuestions.get( 0 ) );
        Assert.assertNotNull( contentService.getQuestionById( dto.getId() ) );
    }

    @Test ( expected = NotFoundException.class )
    public void testGetByIdShouldThrowExcpetion()  {
        contentService.getQuestionById( UUID.randomUUID() );
    }

    @Test
    public void testUpdate()  {
        Answer answer = new Answer();
        List<Answer> answerList = new ArrayList<>();
        answer.setOption( "Testing" );
        answerList.add( answer );
        QuestionResponseDTO dto = contentService.add( listOfQuestions.get( 0 ) );
        Assert.assertNotNull( contentService.update( dto.getId(), answerList ) );
    }

    @Test
    public void testUpdateQuestion()  {
        QuestionResponseDTO dto = contentService.add( listOfQuestions.get( 0 ) );
        Assert.assertNotNull( contentService.update( dto.getId(), new QuestionResponseDTO() ) );
    }

    @Test ( expected = NotFoundException.class )
    public void testUpdateShouldThrowExcpetion()  {
        listOfQuestions.clear();
        contentService.update( UUID.randomUUID(), new QuestionResponseDTO() );
    }

    @Test
    public void testGetSurveyContent() {
        Assert.assertNotNull( contentService.getSurveyContent() );

    }

    @Test
    public void testGetDistributionById() {
        generateSurveyResponse();
        contentService.saveSurveyContent( listOfQuestionsResponse );
        QuestionResponseDTO dto = contentService.getDistributionQuestionById( listOfQuestionsResponse.get( 0 ).getId() );
        Assert.assertNotNull( dto );
    }

    private String returnJsonAsString( String fileName ) throws IOException, URISyntaxException {
        String jsonFile = new String( Files.readAllBytes( Paths.get( getClass().getResource( JSON_BASE_PATH + fileName ).toURI() ) ) );
        return jsonFile;
    }

    private void generateSurveyResponse() {
        List<QuestionResponseDTO> list = contentService.addAll( listOfQuestions );
        listOfQuestionsResponse.forEach( res -> {
            list.forEach( request -> {
                if ( request.getQuestion().equals( res.getQuestion() ) ) {
                    res.setId( request.getId() );
                }
            } );
        } );
    }
}
