package tfip.server.model;

import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

public class Post {
    private String postId;
    private String text;
    private String imgUrl;
    private List<Comment> comments;
    private Integer up;
    private Integer down;
    private String user;
    private LocalDateTime timestamp;

    public Post(String postId, String text, String imgUrl, List<Comment> comments, Integer up, Integer down,
            String user, LocalDateTime timestamp) {
        this.postId = postId;
        this.text = text;
        this.imgUrl = imgUrl;
        this.comments = comments;
        this.up = up;
        this.down = down;
        this.user = user;
        this.timestamp = timestamp;
    }

    public Post() {
        this.comments = new LinkedList<>();
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Integer getUp() {
        return up;
    }

    public void setUp(Integer up) {
        this.up = up;
    }

    public Integer getDown() {
        return down;
    }

    public void setDown(Integer down) {
        this.down = down;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Post [postId=" + postId + ", text=" + text + ", imgUrl=" + imgUrl + ", comments=" + comments + ", up="
                + up
                + ", down=" + down + ", user=" + user + ", timestamp=" + timestamp.toString() + "]";
    }

    public JsonObject toJson() {
        JsonArrayBuilder ab = Json.createArrayBuilder();
        comments.forEach(c -> ab.add(c.toJson()));

        return Json.createObjectBuilder()
                .add("postId", getPostId())
                .add("text", getText())
                .add("imgUrl", getImgUrl())
                .add("comments", ab)
                .add("up", getUp())
                .add("down", getDown())
                .add("user", getUser())
                .build();
    }

    public static Post create(JsonObject o) {
        Post p = new Post();

        p.setPostId(o.getString("postId"));
        p.setText(o.getString("text"));
        p.setTimestamp(LocalDateTime.parse(o.getString("timestamp")));
        p.setUser(o.getString("user"));

        return p;
    }

    public static Post create(String json) {
        return create(Json.createReader(new StringReader(json)).readObject());
    }
}
