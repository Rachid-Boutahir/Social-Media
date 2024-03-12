package com.simplon.publication;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@FeignClient("PUBLICATION")
public interface PublicationServiceClient {
    @GetMapping("/api/v1/posts")
     ResponseEntity<List<PostDTO>> getPosts();
    @PostMapping(path="/api/v1/posts",consumes = {"multipart/form-data","application/json"})
     ResponseEntity<PostDTO> addPost(@RequestParam String postDTOJson, @RequestParam("file") MultipartFile file);
    @GetMapping("/api/v1/posts/{id}")
     ResponseEntity<PostDTO>  getPostById(@PathVariable(value = "id") long id);
    @GetMapping("/api/v1/posts/group/{idgroup}")
     ResponseEntity<List<PostDTO>>  getPostsByGroup(@PathVariable(value = "idgroup") long id);
    @GetMapping("/api/v1/posts/user/{iduser}")
     ResponseEntity<List<PostDTO>>  getPostsByUser(@PathVariable(value = "iduser") long id);
    @PutMapping("/api/v1/posts/{id}")
     ResponseEntity<PostDTO>  updatePost(@RequestBody PostDTO postDTO,@PathVariable(value = "id") long id);
    @DeleteMapping("/api/v1/posts/{id}")
     ResponseEntity<Map<String,Boolean>> deletePost(@PathVariable(value = "id") long id);
}
