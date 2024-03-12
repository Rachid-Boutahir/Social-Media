package com.simplon.media;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FeignClient("MEDIA")
public interface MediaServiceClient {
    @GetMapping("/api/v1/media")
     ResponseEntity<List<MediaDTO>> getAllMedia();
    @PostMapping("/api/v1/media/{postId}")
     ResponseEntity<MediaDTO> addMedia(@RequestParam("file") MultipartFile file, @PathVariable(value = "postId") long postId);

    @GetMapping("/api/v1/media/post/{postId}")
    public ResponseEntity<List<MediaDTO>> getAllMediaByPostId(@PathVariable(value = "postId") long postId);
    @DeleteMapping("/api/v1/media/{fileId}")
     ResponseEntity<Map<String,Boolean>> deleteMedia(@PathVariable(value = "fileId") long fileId);
}
