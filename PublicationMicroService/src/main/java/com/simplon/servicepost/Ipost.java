package com.simplon.servicepost;


import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface Ipost {

    PostDTO addPost(PostDTO post,MultipartFile file);
    boolean deletePost(long postId);
    PostDTO updatePost(PostDTO post,long postId);
    PostDTO getPost(long postId);
    List<PostDTO> getAllPosts();
    List<PostDTO> getPostsByUser(long userId);
    List<PostDTO> getPostsByGroup(long groupId);
    boolean harddeletePost(long postId);

}
