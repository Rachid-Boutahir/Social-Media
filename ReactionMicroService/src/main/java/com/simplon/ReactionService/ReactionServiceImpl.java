package com.simplon.ReactionService;

import com.simplon.notification.NotificationClient;
import com.simplon.notification.NotificationDTO;
import com.simplon.publication.PostDTO;
import com.simplon.publication.PublicationServiceClient;
import com.simplon.user.UserClient;
import com.simplon.user.UserDto;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReactionServiceImpl implements ReactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReactionServiceImpl.class);
    private final ReactionRepository reactionRepository;
    private final ReactionMapper reactionMapper;
    private final PublicationServiceClient publicationServiceClient;
    private final UserClient userClient;
    private final NotificationClient notificationClient;

    @Autowired
    public ReactionServiceImpl(ReactionRepository reactionRepository, ReactionMapper reactionMapper,PublicationServiceClient publicationServiceClient,UserClient userClient, NotificationClient notificationClient) {
        this.reactionMapper = reactionMapper;
        this.reactionRepository = reactionRepository;
        this.publicationServiceClient=publicationServiceClient;
        this.userClient=userClient;
        this.notificationClient=notificationClient;
    }

    @Override
    public Page<ReactionDTO> getAllReactionsByPostId(Long postId,Pageable pageable) {
        try {
            LOGGER.info("Fetching all reactions by post id");
            Page<Reaction> reactions =  reactionRepository.findByPostId(postId,pageable);
            return reactions.map(reactionMapper::toDTO);

        } catch (Exception e) {
            LOGGER.error("Error occurred while fetching all reaction by post id", e);
            throw e;
        }
    }

    @Override
    public Page<ReactionDTO> getAllReactionsByUseId(Long userId, Pageable pageable) {
        try {
            LOGGER.info("Récupération de toutes les réactions par userId");
            Page<Reaction> reactions =reactionRepository.findByUserId(userId, pageable);
            return reactions.map(reactionMapper::toDTO);
        } catch(Exception e) {
            LOGGER.error("Une erreur s'est produite lors de la récupération de toutes les réactions par userId", e);
            throw e;
        }
    }


    public ReactionDTO addReactionToPost(ReactionDTO reactionDTO,UserDto user,PostDTO post ) {
        try {
            LOGGER.info("Service: Adding a new reaction to the post");
            // check if the user has already reacted to that post before adding a new reaction
            Reaction reaction = reactionMapper.toEntity(reactionDTO);
            Reaction newReaction = reactionRepository.save(reaction);
            NotificationDTO notificationDTO = NotificationDTO.builder()
                    .contentNotif("A réagi à votre post")
                    .typeNotif(reactionDTO.getTypeReaction().toString())
                    .readNotif(false)
                    .senderId(user.getIdUser())
                    .recipientId(post.getUserId())
                    .dateNotif(LocalDateTime.now())
                    .build();
            notificationClient.createNotification(notificationDTO);
            return reactionMapper.toDTO(newReaction);

        } catch (Exception e) {
            LOGGER.error("An error has occurred while adding a new reaction to the post", e);
            throw e;
        }
    }


@Override
public ReactionDTO updateReaction(ReactionDTO reactionDTO) {
    try {
        LOGGER.info("Mise à jour de la réaction");
        Reaction existingReaction = reactionMapper.toEntity(reactionDTO);
        Optional<Reaction> existingReactionOptional = reactionRepository.findReactionByUserIdAndPostId(existingReaction.getUserId(), existingReaction.getPostId());
        PostDTO post = publicationServiceClient.getPostById(existingReaction.getPostId()).getBody();
        UserDto user = userClient.getUserById(existingReaction.getUserId()).getBody();
        // TODO: Ajouter la vérification ici
        if (user != null && post != null) {
            if (existingReactionOptional.isPresent()) {
                Reaction existingReactionFromDb = existingReactionOptional.get();
                existingReactionFromDb.setTypeReaction(reactionDTO.getTypeReaction());
                existingReactionFromDb.setDateDeReaction(reactionDTO.getDateDeReaction());
                // TODO: Après avoir ajoute la réaction, envoyer une notification au proprietaire du post
                NotificationDTO notificationDTO = NotificationDTO.builder()
                        .contentNotif("A réagi à votre post")
                        .typeNotif(reactionDTO.getTypeReaction().toString())
                        .readNotif(false)
                        .senderId(user.getIdUser())
                        .recipientId(post.getUserId())
                        .dateNotif(LocalDateTime.now())
                        .build();
                notificationClient.createNotification(notificationDTO);
                return reactionMapper.toDTO(reactionRepository.save(existingReactionFromDb));
            } else {
                return addReactionToPost(reactionDTO,user,post);
            }
        }else{
            throw new RuntimeException("le post ou le user dosn't exist");
        }
    } catch (Exception e) {
        LOGGER.error("Un problème est survenu lors de la tentative de mise à jour de cette réaction", e);
        throw new RuntimeException("Échec de la mise à jour de la réaction", e);
    }

}

    @Override
    public void removeReactionFromAPost(Long id) {
        try {
            LOGGER.info("removing reaction");
            Optional<Reaction> existingReaction = reactionRepository.findById(id);
            if (existingReaction.isEmpty()) {
                throw new EntityNotFoundException("Reaction with id " + id + " not found");
            }
            reactionRepository.deleteById(id);
        }catch(Exception e){
            LOGGER.error("a problem has occured while trying to remove this reaction");
            throw e ;
        }

    }
}
