package com.ssafy.four_paws.post.repository;

import com.ssafy.four_paws.post.embeddable.PostPostPhotoId;
import com.ssafy.four_paws.post.entity.PostPostPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostPostPhotoRepository extends JpaRepository<PostPostPhoto, PostPostPhotoId> {
}
