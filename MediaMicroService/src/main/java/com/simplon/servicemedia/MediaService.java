package com.simplon.servicemedia;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class MediaService implements IMedia{
    private MediaRepository mediaRepository;
    private final Path fileStorageLocation;
    private MapperConfig mapperConfig;
    private static final Logger LOGGER = LoggerFactory.getLogger(MediaService.class);

    @Autowired
    MediaService(MediaRepository mediaRepository1,MapperConfig mapperConfig,FileStorageProperties fileStorageProperties) throws FileStorageException {
        this.mediaRepository = mediaRepository1;
        this.mapperConfig = mapperConfig;
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public MediaDTO addMedia(MultipartFile file,long postId) throws FileStorageException {
        LOGGER.info("Upload file request received.");
        //upload file
        String fileName = this.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/MediaMicroService/uploads/")
                .path(fileName)
                .toUriString();
        //save file's name and url to database
        MediaDTO mediaDTO = new MediaDTO();
        mediaDTO.setName(fileName);
        mediaDTO.setFileUrl(fileDownloadUri);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        mediaDTO.setDateCreation(now.format(dateFormat));
        mediaDTO.setPostId(postId);
        String fullContentType = file.getContentType();
        String[] parts = fullContentType.split("/");
        String type = parts[0];
        if(type.equals("image")){
            mediaDTO.setTypeFile(TypeFile.IMAGE);
        }else if(type.equals("video")){
            mediaDTO.setTypeFile(TypeFile.VIDEO);
        }else if(type.equals("audio")){
            mediaDTO.setTypeFile(TypeFile.AUDIO);
        }
        Media media = mediaRepository.save(this.mapperConfig.modelMapper().map(mediaDTO,Media.class));
        return this.mapperConfig.modelMapper().map(media,MediaDTO.class);
    }

    @Override
    public boolean deleteMedia(long mediaId) {
        try {
            LOGGER.info("Delete file request received.");
            Media media = mediaRepository.findById(mediaId).orElseThrow(() -> new RuntimeException("Error: Media is not found."));
            Path targetLocation = this.fileStorageLocation.resolve(media.getName());
            System.out.println(targetLocation);
            if(Files.deleteIfExists(targetLocation))
            {
                mediaRepository.deleteById(mediaId);
                return true;
            }
            else
                return false;
        } catch (IOException e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public List<MediaDTO> listMedia() {
        LOGGER.info("List all media request received.");
        List<Media> mediaList = mediaRepository.findAll();
        return mediaList.stream().map(media -> this.mapperConfig.modelMapper().map(media,MediaDTO.class)).toList();

    }
    @Override
    public List<MediaDTO> listMediaByPostId(long postId) {
        LOGGER.info("List all media request by post id.");
        List<Media> mediaList = mediaRepository.findAllByPostId(postId);
        return mediaList.stream().map(media -> this.mapperConfig.modelMapper().map(media,MediaDTO.class)).toList();

    }

    public String storeFile(MultipartFile file) throws FileStorageException {
        // Normalize file name
        LOGGER.info("Store file request received.");
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException | FileStorageException ex) {
            LOGGER.error("Error: " + ex.getMessage());
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

}
