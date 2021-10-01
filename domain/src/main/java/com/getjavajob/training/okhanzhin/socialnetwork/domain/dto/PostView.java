package com.getjavajob.training.okhanzhin.socialnetwork.domain.dto;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.post.Post;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static java.time.format.DateTimeFormatter.ofPattern;

public class PostView {
    private static final DateTimeFormatter DATE_TIME_FORMAT = ofPattern("dd.MM.yyyy 'at' HH:mm:ss");

    private Account creator;
    private Post postObject;
    private String publicationDateString;

    public PostView(Account creator, Post postObject) {
        this.creator = creator;
        this.postObject = postObject;
        this.publicationDateString = postObject.getPublicationDate().format(DATE_TIME_FORMAT);
    }

    public Account getCreator() {
        return creator;
    }

    public void setCreator(Account creator) {
        this.creator = creator;
    }

    public Post getPostObject() {
        return postObject;
    }

    public void setPostObject(Post postObject) {
        this.postObject = postObject;
    }

    public String getPublicationDateString() {
        return publicationDateString;
    }

    public void setPublicationDateString(String publicationDateString) {
        this.publicationDateString = publicationDateString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostView postView = (PostView) o;
        return Objects.equals(postObject, postView.postObject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creator, postObject);
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("PostView{").
                append("creator=").append(creator).append('\'').
                append("postContent=").append(postObject.getContent()).append('\'').
                append('}').toString();
    }
}
