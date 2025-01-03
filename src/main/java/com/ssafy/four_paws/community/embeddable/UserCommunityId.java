package com.ssafy.four_paws.community.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCommunityId {
    private Long userId;
    private Long communityId;
}
