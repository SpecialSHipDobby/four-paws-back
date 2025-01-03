package com.ssafy.four_paws.post.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ssafy.four_paws.care_zone.entity.CareZone;
import com.ssafy.four_paws.user.entity.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import lombok.*;

@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "postPostPhoto") // 무한 재귀 방지
@EqualsAndHashCode(exclude = "postPostPhoto") // 무한 재귀 방지
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String title;

    @Column(nullable = false)
    private String content;

    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "care_zone_id")
    private CareZone careZone;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<PostPostPhoto> postPostPhoto = new HashSet<>();
}
