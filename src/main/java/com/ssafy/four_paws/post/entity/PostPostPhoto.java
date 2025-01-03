package com.ssafy.four_paws.post.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ssafy.four_paws.post.embeddable.PostPostPhotoId;
import com.ssafy.four_paws.post_photo.entity.PostPhoto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "post_post_photo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostPostPhoto {

    @EmbeddedId
    private PostPostPhotoId postPostPhotoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")
    @JoinColumn(name = "post_id")
    @JsonBackReference
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postPhotoId")
    @JoinColumn(name = "post_photo_id")
    @JsonBackReference
    private PostPhoto postPhoto;

}
