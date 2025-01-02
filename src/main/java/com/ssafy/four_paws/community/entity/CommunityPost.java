package com.ssafy.four_paws.community.entity;

import com.ssafy.four_paws.community.embeddable.CommunityPostId;
import com.ssafy.four_paws.post.entity.Post;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "community_post")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommunityPost {
    @EmbeddedId
    private CommunityPostId id = new CommunityPostId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("communityId")
    @JoinColumn(name = "community_id")
    private Community community;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")
    @JoinColumn(name = "post_id")
    private Post post;
}
