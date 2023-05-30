package tfip.server.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tfip.server.model.Post;
import tfip.server.repository.ContentRepository;
import tfip.server.repository.ImageRepository;
import tfip.server.repository.VoteRepository;

@Service
public class PostService {
    
    @Autowired
    private ContentRepository contentRepo;

    @Autowired
    private ImageRepository imageRepo;

    @Autowired
    private VoteRepository voteRepo;
    
    public Optional<String> uploadPost(String user, String text, MultipartFile image) {
        String postId = generateUUID();

        try {
            contentRepo.savePost(postId, text, user);
            imageRepo.uploadFile(image, postId); 
            voteRepo.createRecord(postId);   
        } catch (IOException e) {
            return Optional.empty();
        }

        return Optional.of(postId);
    }

    public Optional<Post> getPostById(String postId) {
        try {
            Post p = contentRepo.getPostById(postId);
            p.setImgUrl(imageRepo.getFileUrl(postId).toString());
            p.setUp(voteRepo.getUpvotesByPostId(postId));
            p.setDown(voteRepo.getDownvotesByPostId(postId));
            return Optional.of(p);                
        } catch (Exception e) {
            return Optional.empty();
        } 
    }

    public List<String> getPostIDs(Integer pageNum, Integer limit) {
        return contentRepo.getPostIDs(pageNum, limit);
    }
    
    public void upvote(String postId) {
        voteRepo.upvoteByPostId(postId);
    }

    public void downvote(String postId) {
        voteRepo.downvoteByPostId(postId);
    }

    private String generateUUID() {
        return UUID.randomUUID().toString().substring(0,8);
    }
}
