package com.getjavajob.training.okhanzhin.socialnetwork.domain.post;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "posts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "postType", discriminatorType = DiscriminatorType.STRING)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long postID;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sourceID", nullable = false, updatable = false)
    private Account source;
    private String content;
    private LocalDateTime publicationDate;

    public Post(Account source, String content) {
        this.source = source;
        this.content = content;
    }

    public Post(Account source, String content, LocalDateTime publicationDate) {
        this.source = source;
        this.content = content;
        this.publicationDate = publicationDate;
    }

    public Post(Long postID, Account source, String content, LocalDateTime publicationDate) {
        this.postID = postID;
        this.source = source;
        this.content = content;
        this.publicationDate = publicationDate;
    }

    public Post() {
    }

    public Long getPostID() {
        return postID;
    }

    public void setPostID(Long postID) {
        this.postID = postID;
    }

    public Account getSource() {
        return source;
    }

    public void setSource(Account source) {
        this.source = source;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return source.getAccountID().equals(post.source.getAccountID()) &&
                content.equals(post.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postID, source.getAccountID(), content);
    }
}
