package tfip.server.model;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Comment {
    private String commentId;
    private String text;
    private String user;

    public Comment() {
    }

    public Comment(String commentId, String text, String user) {
        this.commentId = commentId;
        this.text = text;
        this.user = user;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Comment [commentId=" + commentId + ", text=" + text + ", user=" + user + "]";
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("commentId", getCommentId())
                .add("text", getText())
                .add("user", getUser())
                .build();
    }

    
}
