package com.simplon.ReactionService;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReactionServiceImplTest {

    @Mock
    private ReactionRepository reactionRepository;

    @Mock
    private ReactionMapper reactionMapper;

    @InjectMocks
    private ReactionServiceImpl reactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

   @Test
    void testGetAllReactionsByPostId() {
        long postId = 1L;
        Pageable pageable = Pageable.unpaged();
        when(reactionRepository.findByPostId(eq(postId), any(Pageable.class)))
                .thenReturn(Page.empty());
        Page<ReactionDTO> result = reactionService.getAllReactionsByPostId(postId, pageable);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(reactionRepository, times(1)).findByPostId(eq(postId), any(Pageable.class));
    }
    @Test
    void testGetAllReactionsByUserId() {

        long userId = 1L;
        Pageable pageable = Pageable.unpaged();
        when(reactionRepository.findByUserId(eq(userId), any(Pageable.class)))
                .thenReturn(Page.empty());
        Page<ReactionDTO> result = reactionService.getAllReactionsByUseId(userId, pageable);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(reactionRepository, times(1)).findByUserId(eq(userId), any(Pageable.class));
    }


    @Test
    void addReactionToPost() {
        ReactionDTO reactionDTO = new ReactionDTO(1L, 2L, 1L, TypeReaction.LIKE,LocalDateTime.now());
        Reaction reaction = new Reaction();
        when(reactionRepository.findReactionByUserIdAndPostId(anyLong(), anyLong()))
                .thenReturn(Optional.of(new Reaction()));
        when(reactionMapper.toEntity(reactionDTO)).thenReturn(reaction);
        when(reactionMapper.toDTO(reaction)).thenReturn(reactionDTO);
        verify(reactionRepository, never()).save(reaction);
        verify(reactionMapper, never()).toDTO(reaction);
    }


    @Test
    void removeReactionFromAPost_Success() {
        Long reactionId = 1L;
        Reaction existingReaction = new Reaction();
        existingReaction.setId(reactionId);
        when(reactionRepository.findById(reactionId)).thenReturn(Optional.of(existingReaction));
        assertDoesNotThrow(() -> reactionService.removeReactionFromAPost(reactionId));
        verify(reactionRepository, times(1)).deleteById(reactionId);
    }

    @Test
    void removeReactionFromAPost_WhenReactionNotFound() {
        Long reactionId = 1L;
        when(reactionRepository.findById(reactionId)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> reactionService.removeReactionFromAPost(reactionId));

        assertEquals("Reaction with id " + reactionId + " not found", exception.getMessage());
        verify(reactionRepository, never()).deleteById(anyLong());
    }


    @Test
    void testUpdateReaction() {
        ReactionDTO reactionDTO = new ReactionDTO();
        Reaction existingReaction = new Reaction();
        existingReaction.setUserId(1L);
        existingReaction.setPostId(1L);
        Optional<Reaction> existingReactionOptional = Optional.of(existingReaction);
        when(reactionMapper.toEntity(eq(reactionDTO))).thenReturn(existingReaction);
        when(reactionRepository.findReactionByUserIdAndPostId(eq(1L), eq(1L))).thenReturn(existingReactionOptional);
        when(reactionRepository.save(eq(existingReaction))).thenReturn(existingReaction);
        when(reactionMapper.toDTO(eq(existingReaction))).thenReturn(reactionDTO);
        ReactionDTO result = reactionService.updateReaction(reactionDTO);
        assertNotNull(result);
        verify(reactionMapper, times(1)).toEntity(eq(reactionDTO));
        verify(reactionRepository, times(1)).findReactionByUserIdAndPostId(eq(1L), eq(1L));
        verify(reactionRepository, times(1)).save(eq(existingReaction));
        verify(reactionMapper, times(1)).toDTO(eq(existingReaction));
    }



}
