package com.simplon.servicemedia;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IMedia {
     MediaDTO addMedia(MultipartFile file,long postId) throws FileStorageException;
     boolean deleteMedia(long mediaId);
     List<MediaDTO> listMediaByPostId(long postId);
     List<MediaDTO> listMedia();

}
