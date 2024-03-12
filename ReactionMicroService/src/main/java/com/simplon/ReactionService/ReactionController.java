package com.simplon.ReactionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reaction")
public class ReactionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReactionController.class);
    private final ReactionService reactionService;

    @Autowired
    public ReactionController(ReactionService reactionService){
        this.reactionService=reactionService;
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Page<ReactionDTO>> getAllReactionsByPostId(@PathVariable long postId , @RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "10")int size){
        Sort sort = Sort.by("dateDeReaction").descending();
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<ReactionDTO> reactionDTOS=reactionService.getAllReactionsByPostId(postId,pageable);
        LOGGER.info("reactions fetched successfuly ");
        return new ResponseEntity<>(reactionDTOS, HttpStatus.OK);

    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<ReactionDTO>> getAllReactionsByUserId(@PathVariable long userId, @RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "10")int size){
        Sort sort = Sort.by("dateDeReaction").descending();
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<ReactionDTO> reactionDTOS=reactionService.getAllReactionsByUseId(userId,pageable);
        LOGGER.info("reactions fetched successfuly ");
        return new ResponseEntity<>(reactionDTOS, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<ReactionDTO> addingReactionToAPost(
            @RequestBody ReactionDTO reactionDTO
    ){
        LOGGER.info("Controller: Adding reaction to post");
        ReactionDTO addedReaction = reactionService.updateReaction(reactionDTO);
        return new ResponseEntity<>(addedReaction, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAReaction(@PathVariable Long id){
        LOGGER.info("deleting reaction " +id);
        reactionService.removeReactionFromAPost(id);
        return new ResponseEntity<>("reaction deleted successfully", HttpStatus.OK);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<ReactionDTO> updatingAReaction(@PathVariable Long id, @RequestBody ReactionDTO reactionDTO){
//        LOGGER.info("Controller: Updating a  reaction to post");
//        ReactionDTO updatedReaction =reactionService.updateReaction(id,reactionDTO);
//        return new ResponseEntity<>(updatedReaction,HttpStatus.OK);
//    }
}
