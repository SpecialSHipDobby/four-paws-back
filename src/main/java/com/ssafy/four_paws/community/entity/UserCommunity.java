package com.ssafy.four_paws.community.entity;

import com.ssafy.four_paws.community.embeddable.UserCommunityId;
import com.ssafy.four_paws.user.entity.User;
import com.ssafy.four_paws.user.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_community")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCommunity {
    @EmbeddedId
    private UserCommunityId id = new UserCommunityId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("communityId")
    @JoinColumn(name = "community_id")
    private Community community;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
