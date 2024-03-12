package com.simplon.servicepost;

import com.simplon.media.MediaDTO;
import com.simplon.reaction.ReactionDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private long postId;
    private String content;
    private long userId;
    private long groupId;
    private String datePost;
    private boolean deleted = false;
    private List<ReactionDTO> reactions;
    private List<MediaDTO> medias;
}
