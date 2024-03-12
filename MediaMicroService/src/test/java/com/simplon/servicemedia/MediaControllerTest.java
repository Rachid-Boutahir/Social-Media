package com.simplon.servicemedia;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(MediaController.class)
@ExtendWith(SpringExtension.class)
class MediaControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private IMedia mediaService;
    @MockBean
    MapperConfig mapperConfig;

    private MockMultipartFile file;


    @BeforeEach
    public void setUp() {
    }
    @Test
    void getAllMedia() throws Exception {
        MediaDTO mediaDTO = new MediaDTO();
        mediaDTO.setName("13.png");
        mediaDTO.setFileUrl("http://localhost:8080/MediaMicroService/uploads/13.png");
        mediaDTO.setPostId(1L);
        mediaDTO.setTypeFile(TypeFile.IMAGE);
        when(mediaService.listMedia()).thenReturn(List.of(mediaDTO));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/media"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void addMedia() throws Exception {
        // Create a mock multipart file
        file = new MockMultipartFile("file", "13.png", MediaType.IMAGE_PNG_VALUE, "Hello, World!".getBytes(StandardCharsets.UTF_8));
        when(mediaService.addMedia(file, 1L)).thenReturn(new MediaDTO());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/media/1")
                        .file(file))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void deleteMedia() throws Exception {
        long fileId = 1L; // Example fileId
        when(mediaService.deleteMedia(fileId)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/media/{fileId}", fileId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}