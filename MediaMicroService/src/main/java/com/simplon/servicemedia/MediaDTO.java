package com.simplon.servicemedia;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class MediaDTO {
    private long fileId;
    private String name;
    private String fileUrl;
    private TypeFile typeFile;
    private String dateCreation;
    private long postId;


}
