package com.mrcoder.cvector.model;

public class Posts {

    private String postId;
    private String postTitle;
    private String postImage;
    private String postedByUsername;
    private String postedDateTime;

    public Posts(String postId, String postTitle, String postImage, String postedByUsername, String postedDateTime) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postImage = postImage;
        this.postedByUsername = postedByUsername;
        this.postedDateTime = postedDateTime;
    }

    public Posts() {

    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }


    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPostedByUsername() {
        return postedByUsername;
    }

    public void setPostedByUsername(String postedByUsername) {
        this.postedByUsername = postedByUsername;
    }

    public String getPostedDateTime() {
        return postedDateTime;
    }

    public void setPostedDateTime(String postedDateTime) {
        this.postedDateTime = postedDateTime;
    }
}
