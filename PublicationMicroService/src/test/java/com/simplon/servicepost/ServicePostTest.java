package com.simplon.servicepost;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ServicePostTest {

    private Ipost postService;

    @Autowired
    ServicePostTest(Ipost postService1){
        this.postService = postService1;
    }
    PostDTO postDTO,post;
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
        if(this.postService.getPost(post.getPostId())!=null)
            this.postService.harddeletePost(post.getPostId());
    }

    @Test
    void addPost() {
        post= postService.addPost(postDTO,null);
      assertEquals(post.getContent(),postDTO.getContent());
    }

    @Test
    void deletePost() {
         post= postService.addPost(postDTO,null);
        boolean result = postService.deletePost(post.getPostId());
        assertTrue(result);
    }

    @Test
    void updatePost() {
        post= postService.addPost(postDTO,null);
        String content=post.getContent();
        post.setContent("helloworld");
        post.setDatePost("2021-08-01");
        PostDTO postDTO1=postService.updatePost(post,post.getPostId());
        assertNotEquals(content,postDTO1.getContent());
    }

    @Test
    void getPost() {
         post= postService.addPost(postDTO,null);
        assertEquals(postService.getPost(post.getPostId()).getContent(),post.getContent());
    }

    @Test
    void getAllPosts() {
        post= postService.addPost(postDTO,null);
        List<PostDTO> postDTOS=this.postService.getAllPosts();
        assertNotNull(postDTOS);
    }

    @Test
    void getPostsByUser() {
        post= postService.addPost(postDTO,null);
        List<PostDTO> postDTOS=this.postService.getPostsByUser(post.getUserId());
        assertNotNull(postDTOS);
    }
}