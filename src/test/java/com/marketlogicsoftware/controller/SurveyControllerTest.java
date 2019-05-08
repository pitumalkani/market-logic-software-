/**
 * 
 */
package com.marketlogicsoftware.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
public class SurveyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestUtil testUtil;

    private static final String SURVEY = "SurveyResponse.json";

    @Test
    public void testSurveySuccess() throws Exception {
        mockMvc.perform( get( "/survey" ).accept( MediaType.parseMediaType( MediaType.APPLICATION_JSON_VALUE ) ) ).andExpect( status().isOk() )
               .andExpect( content().contentType( MediaType.APPLICATION_JSON_UTF8_VALUE ) );
    }

    @Test
    public void testAddForSuccess() throws Exception {
        mockMvc.perform( post( "/survey" ).contentType( MediaType.APPLICATION_JSON ).content( testUtil.returnJsonAsString( SURVEY ) ).accept( MediaType.APPLICATION_JSON ) )
               .andExpect( status().isCreated() );
    }
}
