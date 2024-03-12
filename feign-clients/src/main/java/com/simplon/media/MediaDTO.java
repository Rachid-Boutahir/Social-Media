package com.simplon.media;


import lombok.Data;

@Data
public class MediaDTO {
    private long fileId;
    private String name;
    private String fileUrl;
    private TypeFile typeFile;
    private String dateCreation;
    private long postId;
}