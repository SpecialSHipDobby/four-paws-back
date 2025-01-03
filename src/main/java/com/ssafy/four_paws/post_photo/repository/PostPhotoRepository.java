package com.ssafy.four_paws.post_photo.repository;

import com.ssafy.four_paws.post_photo.entity.PostPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostPhotoRepository extends JpaRepository<PostPhoto, Long> {
}
