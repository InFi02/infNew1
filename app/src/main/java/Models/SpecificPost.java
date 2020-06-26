package Models;

public class SpecificPost {

    String addToHighlights;
    String description;
    String postImage;
    String postid;

    public SpecificPost(String addToHighlights, String description, String postImage, String postid) {
        this.addToHighlights = addToHighlights;
        this.description = description;
        this.postImage = postImage;
        this.postid = postid;
    }

    public SpecificPost() {
    }

    public String getAddToHighlights() {
        return addToHighlights;
    }

    public void setAddToHighlights(String addToHighlights) {
        this.addToHighlights = addToHighlights;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }
}
