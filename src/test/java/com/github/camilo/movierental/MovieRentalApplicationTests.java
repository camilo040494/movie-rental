package com.github.camilo.movierental;

import static com.github.camilo.movierental.mapper.MovieMapperTest.DESCRIPTION;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.camilo.movierental.mapper.MovieMapperTest;

@SpringBootTest
class MovieRentalApplicationTests {

    private MockMvc mockMvc;
    
    @Autowired
    private WebApplicationContext wac;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    
    @Test
    void contextLoads() {
        try {
            mockMvc.perform(get("/movies/3")).andExpect(status().isNotFound());
        } catch (Exception e) {
            fail();
        }
    }
    @Test
    void contextLoadsAdd() {
        try {
            String content = objectMapper.writeValueAsString(MovieMapperTest.buildMovieDto());
            System.out.println(content);
            System.out.println("---");
            MvcResult andReturn = mockMvc.perform(post("/movies")
                    .contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description").value(DESCRIPTION)).andReturn();
            System.out.println("-+-");
            System.out.println(andReturn.getResponse().toString());
            System.out.println("-+-");
        } catch (Exception e) {
            fail(e.getMessage()+"\n"+e.getLocalizedMessage()+"\n"+e.getCause().toString());
        }
    }
    
}
