package com.getjavajob.training.okhanzhin.socialnetwork.domain.picture;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "comm_pictures")
public class CommunityPicture extends Picture implements Serializable {
    private static final long serialVersionUID = -2957836497971530558L;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "commID")
    private Community community;

    public CommunityPicture(Long picID, Community community, byte[] content) {
        super(picID, content);
        this.community = community;
    }

    public CommunityPicture() {
    }

    public CommunityPicture(byte[] content) {
        super(content);
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommunityPicture)) return false;
        if (!super.equals(o)) return false;
        CommunityPicture that = (CommunityPicture) o;
        return Objects.equals(community.getCommID(), that.community.getCommID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), community.getCommID());
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("CommunityPicture{").
                append("commID=").append(community.getCommID()).
                append('}').toString();
    }
}