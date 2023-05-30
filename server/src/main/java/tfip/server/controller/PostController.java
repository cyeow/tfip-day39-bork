package tfip.server.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import tfip.server.model.Post;
import tfip.server.service.PostService;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostController {

    @Autowired
    private PostService postSvc;

    @PostMapping(path = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadPost(@RequestPart String text, @RequestPart MultipartFile img) {
        Optional<String> result = postSvc.uploadPost("dory", text, img);

        if (!result.isPresent()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            Json.createObjectBuilder()
                                    .add("message", "error uploading post. please try again")
                                    .build().toString());
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        Json.createObjectBuilder()
                                .add("id", result.get())
                                .build().toString());
    }

    @GetMapping(path = "/post")
    public ResponseEntity<String> getPost(@RequestParam String postId) {

        Optional<Post> optP = postSvc.getPostById(postId);

        if (optP.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            Json.createObjectBuilder()
                                    .add("message", "No post with postId %s found.".formatted(postId))
                                    .build().toString());
        }

        return ResponseEntity.ok(optP.get().toJson().toString());
    }

    @GetMapping(path = "/posts")
    public ResponseEntity<String> getPostsById(@RequestParam Integer pageNum, @RequestParam Integer limit) {
        List<String> result = postSvc.getPostIDs(pageNum, limit);
        if (result == null || result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            Json.createObjectBuilder()
                                    .add("message", "No posts found.")
                                    .build().toString());
        }
        String jsonResult = Json.createArrayBuilder(result).build().toString();
        return ResponseEntity.status(HttpStatus.OK)
                .body(jsonResult);
    }

    @PutMapping(path = "/post/{postId}/up")
    public ResponseEntity<String> upvotePost(@PathVariable String postId) {
        System.out.println("here");
        postSvc.upvote(postId);
        return ResponseEntity.ok(
                Json.createObjectBuilder()
                        .add("message", "Upvoted post %s".formatted(postId))
                        .build().toString());
    }

    @PutMapping(path = "/post/{postId}/down")
    public ResponseEntity<String> downvotePost(@PathVariable String postId) {
        postSvc.downvote(postId);
        return ResponseEntity.ok(
                Json.createObjectBuilder()
                        .add("message", "Upvoted post %s".formatted(postId))
                        .build().toString());
    }

}
