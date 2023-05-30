package tfip.server.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VoteRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String upvoteKey = "up";
    private static final String downvoteKey = "down";

    public void createRecord(String postId) {
        redisTemplate.opsForHash().put(postId, upvoteKey, 0);
        redisTemplate.opsForHash().put(postId, downvoteKey, 0);
    }

    public Integer getUpvotesByPostId(String postId) {
        return (Integer) redisTemplate.opsForHash().get(postId, upvoteKey);
    }

    public Integer getDownvotesByPostId(String postId) {
        return (Integer) redisTemplate.opsForHash().get(postId, downvoteKey);
    }

    public void upvoteByPostId(String postId) {
        redisTemplate.opsForHash().put(
            postId, upvoteKey, ((Integer) redisTemplate.opsForHash().get(postId, upvoteKey)) + 1
        );
    }

    public void downvoteByPostId(String postId) {
        redisTemplate.opsForHash().put(
            postId, downvoteKey, 
            (Integer) redisTemplate.opsForHash().get(postId, downvoteKey) + 1
        );
    }
}
