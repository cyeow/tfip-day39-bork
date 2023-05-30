package tfip.server.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import tfip.server.model.Post;

@Repository
public class ContentRepository {

    @Autowired
    private MongoTemplate template;

    private static final String COLLECTION_POSTS = "posts";

    public void savePost(String postId, String text, String user) {
        Document d = new Document()
                .append("postId", postId)
                .append("text", text)
                .append("user", user)
                .append("timestamp", LocalDateTime.now().toString());

        template.insert(d, COLLECTION_POSTS);
    }

    public Post getPostById(String postId) {
        Query q = new Query()
                .addCriteria(Criteria.where("postId").is(postId));

        Document doc = template.findOne(q, Document.class, COLLECTION_POSTS);
        Post p = Post.create(doc.toJson());
        return p;
    }

    public List<String> getPostIDs(Integer pageNum, Integer limit) {
        Query q = new Query().skip((pageNum - 1) * limit).limit(limit);

        return template.findDistinct(q, "postId", COLLECTION_POSTS, String.class);
    }
}
