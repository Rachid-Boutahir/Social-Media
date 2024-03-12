package com.simplon.servicepost;

import com.simplon.media.MediaDTO;
import com.simplon.media.MediaServiceClient;
import com.simplon.notification.NotificationClient;
import com.simplon.notification.NotificationDTO;
import com.simplon.reaction.ReactionClient;
import com.simplon.reaction.ReactionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PostService implements Ipost {
    private PostRepository postRepository;
    private final MediaServiceClient mediaServiceClient;
    private final ReactionClient reactionsClient;
    private final NotificationClient notificationClient;
    private MapperConfig mapper;
    @Autowired
    PostService(PostRepository postRepository, MediaServiceClient mediaServiceClient, ReactionClient reactionsClient, NotificationClient notificationClient, MapperConfig mapper){
        this.postRepository = postRepository;
        this.mediaServiceClient = mediaServiceClient;
        this.reactionsClient = reactionsClient;
        this.notificationClient = notificationClient;
        this.mapper = mapper;
    }
    @Override
    public PostDTO addPost(PostDTO post,MultipartFile file){
        //Check Post if null
        if (post == null) throw new IllegalArgumentException("Post cannot be null");
        //Date Time Now Formatter
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        post.setDatePost(now.format(dateFormat));
        //Save Post
        Post postEntity = postRepository.save(mapper.modelMapper().map(post, Post.class));
        //Save Media
       if(file != null){
           MediaDTO media = mediaServiceClient.addMedia(file, postEntity.getPostId()).getBody();
            System.out.println(media);
       }
       //Create Notification
        NotificationDTO notification =new NotificationDTO();
        notification.setContentNotif("New post");
        notification.setTypeNotif("POST");
        notification.setSenderId(postEntity.getUserId());
        LocalDateTime nowNotif = LocalDateTime.now();
        notification.setDateNotif(nowNotif);
        notificationClient.createNotification(notification);
        //Return Post
        return this.mapper.modelMapper().map(postEntity, PostDTO.class);
    }

    @Override
    public boolean deletePost(long postId) {
        //Get Post by ID
        Post post = postRepository.findById(postId).orElse(null);
        //Check if Post is null
        if (post == null)throw new IllegalArgumentException("Post not found");
        //Set Post Deleted
        post.setDeleted(true);
        //Get and Delete Reactions
        Page<ReactionDTO> reactions = this.reactionsClient.getAllReactionsByPostId(post.getPostId(),1,10).getBody();
        reactions.forEach(reactionDTO -> {
            this.reactionsClient.deleteAReaction(reactionDTO.getId());
        });
        //Get and Delete Medias
        List<MediaDTO> media = this.mediaServiceClient.getAllMedia().getBody();
        media.forEach(mediaDTO -> {
            if(mediaDTO.getPostId() == post.getPostId()){
                this.mediaServiceClient.deleteMedia(mediaDTO.getFileId());
            }
        });
        //Return result
        return postRepository.save(post).isDeleted();
    }

    @Override
    public PostDTO updatePost(PostDTO post,long postId) {
        //Get Post by ID
        Post postEntity = postRepository.findById(postId).orElse(null);
        //Check if Post is null
        if (postEntity == null) throw new IllegalArgumentException("Post not found");
        //Update Post
        postEntity = postRepository.save(this.mapper.modelMapper().map(post, Post.class));
        //Return Post
        return this.mapper.modelMapper().map(postEntity, PostDTO.class);
    }

    @Override
    public PostDTO getPost(long postId) {
        //Get Post by ID
        Post post = postRepository.findById(postId).orElse(null);
        //Check if Post is null
        if (post == null) throw new IllegalArgumentException("Post not found");
        //Map Post to PostDTO
        PostDTO postDTO = this.mapper.modelMapper().map(post, PostDTO.class);
        //Get Reactions and Medias
        Page<ReactionDTO> reactions = this.reactionsClient.getAllReactionsByPostId(postDTO.getPostId(),1,10).getBody();
        postDTO.setReactions(reactions.getContent());
        List<MediaDTO> media = this.mediaServiceClient.getAllMedia().getBody();
        postDTO.setMedias(media);
        //Return PostDTO
        return postDTO;
    }

    @Override
    public List<PostDTO> getAllPosts() {
        //Get All Posts
        List<Post> posts=postRepository.findByDeletedFalse();
        //Map Posts to PostDTO
        List<PostDTO> postDTOS = posts.stream().map(c->this.mapper.modelMapper().map(c,PostDTO.class)).toList();
        //Get Reactions and Medias
        postDTOS.forEach(post -> {
            Page<ReactionDTO> reactions = this.reactionsClient.getAllReactionsByPostId(post.getPostId(),1,10).getBody();
            post.setReactions(reactions.getContent());
            List<MediaDTO> media = this.mediaServiceClient.getAllMedia().getBody();
            post.setMedias(media);
        });
        return postDTOS;
    }

    @Override
    public List<PostDTO> getPostsByUser(long userId) {
        //Get Posts by User
        List<Post> posts=postRepository.findByUserIdAndDeletedFalse(userId);
        //Check if Posts is null
        if (posts == null) throw new IllegalArgumentException("Post not found");
        //Map Posts to PostDTO
        List<PostDTO> postDTOS = posts.stream().map(c->this.mapper.modelMapper().map(c,PostDTO.class)).toList();
        //Get Reactions and Medias
        postDTOS.forEach(post -> {
            Page<ReactionDTO> reactions = this.reactionsClient.getAllReactionsByPostId(post.getPostId(),1,10).getBody();
            post.setReactions(reactions.getContent());
            List<MediaDTO> media = this.mediaServiceClient.getAllMedia().getBody();
            post.setMedias(media);
        });
        //Return PostDTO
        return postDTOS;
    }
    @Override
    public List<PostDTO> getPostsByGroup(long groupId) {
        //Get Posts by Group
        List<Post> posts=postRepository.findByGroupIdAndDeletedFalse(groupId);
        //Check if Posts is null
        if (posts == null) throw new IllegalArgumentException("Post not found");
        //Map Posts to PostDTO
        List<PostDTO> postDTOS = posts.stream().map(c->this.mapper.modelMapper().map(c,PostDTO.class)).toList();
        //Get Reactions and Medias
        postDTOS.forEach(post -> {
            Page<ReactionDTO> reactions = this.reactionsClient.getAllReactionsByPostId(post.getPostId(),1,10).getBody();
            post.setReactions(reactions.getContent());
            List<MediaDTO> media = this.mediaServiceClient.getAllMedia().getBody();
            post.setMedias(media);
        });
        //Return PostDTO
        return postDTOS;
    }

    public boolean harddeletePost(long postId) {
        //This is a hard delete for Testing Only and should not be used in production
        //Get Post by ID
        Post post = postRepository.findById(postId).orElse(null);
        //Check if Post is null
        if (post == null)throw new IllegalArgumentException("Post not found");
        //Delete Post
        postRepository.delete(post);
        return true;
    }

}
