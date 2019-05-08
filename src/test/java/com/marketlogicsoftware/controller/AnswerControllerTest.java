/**
 * 
 */
package com.marketlogicsoftware.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

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
public class AnswerControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private TestUtil testUtil;
    
    private static final String ANSWER = "Answer.json";

    @Test
    public void testUpdate() throws Exception {
        String url = "/question/" + UUID.randomUUID() + "/answer";
        mockMvc.perform(put( url).contentType( MediaType.APPLICATION_JSON ).content( testUtil.returnJsonAsString( ANSWER ) ).accept( MediaType.APPLICATION_JSON ) )
        .andExpect( status().isOk() );

    }
}
