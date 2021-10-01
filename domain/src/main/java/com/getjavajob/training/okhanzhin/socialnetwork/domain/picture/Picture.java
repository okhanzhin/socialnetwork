package com.getjavajob.training.okhanzhin.socialnetwork.domain.picture;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Arrays;

@MappedSuperclass
public abstract class Picture implements Serializable {
    private static final long serialVersionUID = -8355188435629970719L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long picID;
    @Lob
    private byte[] content;

    public Picture(Long picID, byte[] content) {
        this.picID = picID;
        this.content = content;
    }

    public Picture() {
    }

    public Picture(byte[] content) {
        this.content = content;
    }

    public Long getPicID() {
        return picID;
    }

    public void setPicID(Long picID) {
        this.picID = picID;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Picture)) return false;
        Picture picture = (Picture) o;
        return Arrays.equals(content, picture.content);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(content);
    }
}
