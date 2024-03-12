package com.simplon.ReactionService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ReactionControllerTest {

    @Mock
    private ReactionService reactionService;

    @InjectMocks
    private ReactionController reactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllReactionsByPostId() {
        long postId = 1L;
        int page = 0;
        int size = 10;

        List<ReactionDTO> fakeReactions = Arrays.asList(
                new ReactionDTO(1L, 1L, postId, TypeReaction.LIKE, LocalDateTime.now()),
                new ReactionDTO(2L, 2L, postId, TypeReaction.DISLIKE, LocalDateTime.now())

        );

        Pageable pageable = PageRequest.of(page, size, Sort.by("dateDeReaction").descending());
        Page<ReactionDTO> fakePage = new PageImpl<>(fakeReactions, pageable, fakeReactions.size());
        when(reactionService.getAllReactionsByPostId(eq(postId), eq(pageable))).thenReturn(fakePage);
        ResponseEntity<Page<ReactionDTO>> responseEntity = reactionController.getAllReactionsByPostId(postId, page, size);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(fakeReactions.size(), responseEntity.getBody().getContent().size());
        verify(reactionService).getAllReactionsByPostId(eq(postId), eq(pageable));
    }

    @Test
    void testGetAllReactionsByUserId() {
        long userId = 2L;
        int page = 0;
        int size = 10;

        List<ReactionDTO> fakeReactions = Arrays.asList(
                new ReactionDTO(1L, userId, 3L, TypeReaction.LIKE, LocalDateTime.now()),
                new ReactionDTO(2L, userId, 5L, TypeReaction.DISLIKE, LocalDateTime.now())

        );

        Pageable pageable = PageRequest.of(page, size, Sort.by("dateDeReaction").descending());
        Page<ReactionDTO> fakePage = new PageImpl<>(fakeReactions, pageable, fakeReactions.size());
        when(reactionService.getAllReactionsByUseId(eq(userId), eq(pageable))).thenReturn(fakePage);
        ResponseEntity<Page<ReactionDTO>> responseEntity = reactionController.getAllReactionsByUserId(userId, page, size);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(fakeReactions.size(), responseEntity.getBody().getContent().size());
        verify(reactionService).getAllReactionsByUseId(eq(userId), eq(pageable));
    }

    @Test
    void addingReactionToAPost() {

        long postId = 1L;
        ReactionDTO inputReactionDTO = new ReactionDTO(1L, 1L, postId, TypeReaction.LIKE, LocalDateTime.now());
        ReactionDTO mockAddedReactionDTO = new ReactionDTO(1L, 1L, postId, TypeReaction.LIKE, LocalDateTime.now());
        when(reactionService.updateReaction(inputReactionDTO)).thenReturn(mockAddedReactionDTO);
        ResponseEntity<ReactionDTO> responseEntity = reactionController.addingReactionToAPost(inputReactionDTO);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(mockAddedReactionDTO, responseEntity.getBody());
    }

    @Test
    void deleteAReaction() {
        Long reactionId = 1L;
        ResponseEntity<String> responseEntity = reactionController.deleteAReaction(reactionId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("reaction deleted successfully", responseEntity.getBody());
        verify(reactionService, times(1)).removeReactionFromAPost(reactionId);
    }
}
