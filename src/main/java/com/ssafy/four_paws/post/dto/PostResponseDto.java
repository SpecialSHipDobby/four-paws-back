package com.ssafy.four_paws.post.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponseDto {
    private long id;
    private String title;
    private String content;
    private String nickname;
    private Long userId;
    private Long careZoneId;
    private String careZoneName;
    private List<String> photoUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
