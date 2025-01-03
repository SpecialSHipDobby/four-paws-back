package com.ssafy.four_paws.post_photo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ssafy.four_paws.post.entity.PostPostPhoto;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import lombok.*;

@Entity
@Table(name = "post_photo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "postPostPhoto") // 무한 재귀 방지
@EqualsAndHashCode(exclude = "postPostPhoto") // 무한 재귀 방지
public class PostPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 500)
    private String photoUrl;

    @Column
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "postPhoto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<PostPostPhoto> postPostPhoto = new HashSet<>();

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "post_id", nullable = false)
//    @JsonManagedReference
//    private Post post;
}
