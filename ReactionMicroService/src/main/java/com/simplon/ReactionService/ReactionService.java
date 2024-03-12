package com.simplon.ReactionService;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ReactionService {
  Page<ReactionDTO> getAllReactionsByPostId(Long postId, Pageable pageable);
  Page<ReactionDTO> getAllReactionsByUseId(Long userId,Pageable pageable);

  void removeReactionFromAPost(Long id);
  ReactionDTO updateReaction(ReactionDTO reactionDTO);

}
