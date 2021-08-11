package org.spring101.urlshortener.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.spring101.urlshortener.config.TestConfig;
import org.spring101.urlshortener.model.UriEncodeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 */
@ActiveProfiles("test")
@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
public class UrlControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper mapper;
    
    @Test
    public void create() throws Exception {
        UriEncodeRequest request = new UriEncodeRequest("http://google.com");
        String json = mapper.writeValueAsString(request);
        this.mockMvc.perform(post("/url-shortener/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.identifier").exists())
                .andExpect(jsonPath("$.identifier").isString());
    }
    
    @Test
    public void nullURI() throws Exception {
        UriEncodeRequest request = new UriEncodeRequest();
        String json = mapper.writeValueAsString(request);
        this.mockMvc.perform(post("/url-shortener/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("URI should be not null"));
    }
    
    @Test
    public void emptyURI() throws Exception {
        UriEncodeRequest request = new UriEncodeRequest("");
        String json = mapper.writeValueAsString(request);
        this.mockMvc.perform(post("/url-shortener/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("URI \"\" is invalid"));
    }
    
    @Test
    public void invalidURI() throws Exception {
        UriEncodeRequest request = new UriEncodeRequest("invalid");
        String json = mapper.writeValueAsString(request);
        this.mockMvc.perform(post("/url-shortener/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("URI \"invalid\" is invalid"));
    }
    
    @Test
    public void missingURI() throws Exception {
        this.mockMvc.perform(get("/url-shortener/invalid"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("No identifier \"invalid\" found"));
    }
    
}
