/**
 * 
 */
package com.marketlogicsoftware.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import javax.ws.rs.NotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.marketlogicsoftware.Application;
import com.marketlogicsoftware.util.TestUtil;

@RunWith ( SpringRunner.class )
@SpringBootTest ( webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = {Application.class} )
@ComponentScan ( basePackages = {"com.marketlogicsoftware"} )
@AutoConfigureMockMvc
public class QuestionControllerTest {

    @Autowired
    private TestUtil testUtil;

    @Autowired
    private MockMvc mockMvc;


    private static final String QUESTION = "Question.json";

    private static final String FILE_LIST_OF_QUESTIONS = "ListOfQuestions.json";

    @Test
    public void testAddForSuccess() throws Exception {
        mockMvc.perform( post( "/question" ).contentType( MediaType.APPLICATION_JSON ).content( testUtil.returnJsonAsString( QUESTION ) ).accept( MediaType.APPLICATION_JSON ) )
               .andExpect( status().isCreated() );
    }

    @Test
    public void testAddAllForSuccess() throws Exception {
        mockMvc.perform( post( "/question/all" ).contentType( MediaType.APPLICATION_JSON ).content( testUtil.returnJsonAsString( FILE_LIST_OF_QUESTIONS ) ).accept( MediaType.APPLICATION_JSON ) )
               .andExpect( status().isCreated() );
    }

    @Test
    public void testGetAllForSuccess() throws Exception {
        mockMvc.perform( get( "/question" ).accept( MediaType.parseMediaType( MediaType.APPLICATION_JSON_VALUE ) ) ).andExpect( status().isOk() )
               .andExpect( content().contentType(  MediaType.APPLICATION_JSON_UTF8_VALUE ) );
    }

    @Test
    public void testGetByid() throws Exception {
        String url = "/question/" + UUID.randomUUID();
        org.assertj.core.api.Assertions.assertThatThrownBy( () -> mockMvc.perform( get( url ) ).andExpect( status().isOk() ) )
                                       .hasCause( new NotFoundException( "Unable to fetch question..Invalid question id" ) );

    }

    @Test
    public void testGetDistributionById() throws Exception {
        String url = "/question/" + UUID.randomUUID() + "/distribution";
        mockMvc.perform( get( url ) ).andExpect( status().isOk() );

    }

    @Test
    public void testDelete() throws Exception {
        String url = "/question/" + UUID.randomUUID();
        org.assertj.core.api.Assertions.assertThatThrownBy( () -> mockMvc.perform( delete( url ) ).andExpect( status().isOk() ) )
                                       .hasCause( new NotFoundException( "Unable to fetch question..Invalid question id" ) );

    }

    @Test
    public void testUpdate() throws Exception {
        String url = "/question/" + UUID.randomUUID();
        mockMvc.perform( put( url ) );

    }
}
