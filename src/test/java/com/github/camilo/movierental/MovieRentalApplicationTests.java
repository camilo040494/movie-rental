package com.github.camilo.movierental;

import static com.github.camilo.movierental.mapper.MovieMapperTest.DESCRIPTION;
import static com.github.camilo.movierental.mapper.UserMapperTest.EMAIL;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.camilo.movierental.mapper.MovieMapperTest;
import com.github.camilo.movierental.mapper.UserMapperTest;
import com.github.camilo.movierental.messages.MovieDto;

@SpringBootTest
@ActiveProfiles("test")
class MovieRentalApplicationTests {

    private static final String VALID_IMAGE = "src/test/resources/movie_image.png";
    private static final String INVALID_IMAGE = "src/test/resources/invalid_image.txt";

    private MockMvc mockMvc;
    
    @Autowired
    private WebApplicationContext wac;
    
    @Autowired
    private ObjectMapper objectMapperTest;
    
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    
    @Test
    void contextLoadsMovieNotFound() {
        try {
            mockMvc.perform(get("/movies/3")).andExpect(status().isNotFound());
        } catch (Exception e) {
            fail();
        }
    }
    
    @Test
    void contextLoadsAddMovieInvalidImageExtension() throws Exception {
        MovieDto buildMovieDto = MovieMapperTest
                .buildMovieDto();
        buildMovieDto.setImage(buildFromImage(INVALID_IMAGE));
        String content;
        try {
            content = objectMapperTest.writeValueAsString(buildMovieDto);
            mockMvc.perform(post("/movies")
                    .contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpect(status().isBadRequest());
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            fail(e.getMessage());
        }
    }
    
//    @Test
    void contextLoadsAddMovieInvalidSize() throws Exception {
        MovieDto buildMovieDto = MovieMapperTest
                .buildMovieDto();
        buildMovieDto.setImage(buildFromImage("src/test/resources/zHNEnaG.png"));
        String content;
        try {
            content = objectMapperTest.writeValueAsString(buildMovieDto);
            mockMvc.perform(post("/movies")
                    .contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpect(status().isBadRequest());
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    void contextLoadsAdd() throws Exception {
        try {
            String userContent = objectMapperTest.writeValueAsString(UserMapperTest
                    .buildUserDto());
            mockMvc.perform(post("/users")
                    .contentType(MediaType.APPLICATION_JSON).content(userContent))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.email").value(EMAIL));
            
            MovieDto buildMovieDto = MovieMapperTest
                    .buildMovieDto();
            buildMovieDto.setImage(buildFromImage(VALID_IMAGE));
            String content = objectMapperTest.writeValueAsString(buildMovieDto);
            MvcResult response = mockMvc.perform(post("/movies")
                    .contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.description").value(DESCRIPTION)).andReturn();
            String contentAsString = response.getResponse().getContentAsString();
            
            System.out.println(contentAsString);
            
            Principal principal = new Principal() {
                @Override
                public String getName() {
                    return EMAIL;
                }
            };
            MovieDto readValue = objectMapperTest.readValue(contentAsString, MovieDto.class);
            Long movie = readValue.getId();
            MvcResult andReturn = mockMvc.perform(post("/charge/rent/"+movie).principal(principal)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()).andReturn();
            String transaction = andReturn.getResponse().getContentAsString();
            System.out.println(transaction);
            
            MvcResult buyOperation = mockMvc.perform(post("/charge/buy/"+movie).principal(principal)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()).andReturn();
            String buyTransaction = buyOperation.getResponse().getContentAsString();
            System.out.println(buyTransaction);
            
            MvcResult profitsResult = mockMvc.perform(get("/charge/profits").principal(principal))
                    .andExpect(status().isOk()).andReturn();
            System.out.println(profitsResult.getResponse().getContentAsString());
            
            mockMvc.perform(get("/movies")
                    .queryParam("page", "0")
                    .queryParam("numberOfResults", "1"))
            .andExpect(status().isOk());
            
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            fail(e.getMessage()+"\n"+e.getLocalizedMessage()+"\n"+e.getCause().toString());
        }
    }

    private String buildFromImage(String image) {
        File file = new File(image);
        byte[] readFileToByteArray = new byte[] {};
        try {
            readFileToByteArray = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            fail(e.getMessage());
        }
        return Base64.encodeBase64String(readFileToByteArray);
    }
    
}
