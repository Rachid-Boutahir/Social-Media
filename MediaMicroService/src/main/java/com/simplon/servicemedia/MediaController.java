package com.simplon.servicemedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/media")
public class MediaController {
    private IMedia mediaService;
    @Autowired
    public MediaController(IMedia mediaService) {
        this.mediaService = mediaService;
    }

    @GetMapping
    public ResponseEntity<List<MediaDTO>> getAllMedia() {
        return new ResponseEntity<>(mediaService.listMedia(), HttpStatus.OK);
    }
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<MediaDTO>> getAllMediaByPostId(@PathVariable(value = "postId") long postId) {
        return new ResponseEntity<>(mediaService.listMediaByPostId(postId), HttpStatus.OK);
    }
    @PostMapping(path="/{postId}",consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<MediaDTO> addMedia(@RequestParam(value = "file") MultipartFile file, @PathVariable(value = "postId") long postId) throws FileStorageException {
        System.out.println("file : "+file.getOriginalFilename());
        return new ResponseEntity<>(mediaService.addMedia(file, postId), HttpStatus.CREATED);
    }

   @DeleteMapping("/{fileId}")
    public ResponseEntity<Map<String,Boolean>> deleteMedia(@PathVariable(value = "fileId") long fileId) {
        Map<String,Boolean> response=new HashMap<>();
        if(mediaService.deleteMedia(fileId))
            response.put("Deleted",true);
        else
            response.put("Deleted",true);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
