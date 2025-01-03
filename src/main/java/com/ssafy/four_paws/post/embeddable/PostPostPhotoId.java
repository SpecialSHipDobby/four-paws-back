package com.ssafy.four_paws.post.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostPostPhotoId implements Serializable {
    private Long postId;
    private Long postPhotoId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostPostPhotoId that = (PostPostPhotoId) o;

        if (!Objects.equals(postId, that.postId)) return false;
        return Objects.equals(postPhotoId, that.postPhotoId);
    }

    @Override
    public int hashCode() {
        int result = postId != null ? postId.hashCode() : 0;
        result = 31 * result + (postPhotoId != null ? postPhotoId.hashCode() : 0);
        return result;
    }
}
