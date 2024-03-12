package com.simplon.servicepost;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
@ExtendWith(SpringExtension.class)
class PostControllerTest {

    @MockBean
    private Ipost postService;
    @MockBean
    MapperConfig mapperConfig;
    @Autowired
    MockMvc mockMvc;

    PostDTO postDTO;

    @BeforeEach
    void setUp() {
        postDTO = new PostDTO();
        postDTO.setContent("Hello");
        postDTO.setUserId(1);
        postDTO.setDatePost("2021-07-01");
        postDTO.setDeleted(false);

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void getPosts() {
    }

    @Test
    void addPost() throws Exception {
        when(postService.addPost(postDTO,null)).thenReturn(postDTO);
        mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(postDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void getPostById() throws Exception{
        when(postService.getPost(1)).thenReturn(postDTO);
        mockMvc.perform(get("/api/v1/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(postDTO)))
                .andExpect(status().isOk());

    }

    @Test
    void updatePost() throws Exception{
        when(postService.updatePost(postDTO,1)).thenReturn(postDTO);
        mockMvc.perform(put("/api/v1/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(postDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void deletePost() throws Exception{
        when(postService.deletePost(1)).thenReturn(true);
        mockMvc.perform(delete("/api/v1/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(postDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void getPostsByUser() throws Exception{
        when(postService.getPostsByUser(1)).thenReturn(List.of(postDTO));
        mockMvc.perform(get("/api/v1/posts/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(postDTO)))
                .andExpect(status().isOk());
    }
}