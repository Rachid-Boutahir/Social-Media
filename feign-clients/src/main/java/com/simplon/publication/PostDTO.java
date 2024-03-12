package com.simplon.publication;

import lombok.*;

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
    private boolean deleted;
}
