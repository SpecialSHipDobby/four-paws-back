package com.ssafy.four_paws.post.service;

import com.ssafy.four_paws.care_zone.entity.CareZone;
import com.ssafy.four_paws.care_zone.repository.CareZoneRepository;
import com.ssafy.four_paws.exception.CustomException;
import com.ssafy.four_paws.file.FileStorageService;
import com.ssafy.four_paws.post.dto.PostCreateDto;
import com.ssafy.four_paws.post.dto.PostResponseDto;
import com.ssafy.four_paws.post.embeddable.PostPostPhotoId;
import com.ssafy.four_paws.post.entity.Post;
import com.ssafy.four_paws.post.entity.PostPostPhoto;
import com.ssafy.four_paws.post.repository.PostPostPhotoRepository;
import com.ssafy.four_paws.post.repository.PostRepository;
import com.ssafy.four_paws.post_photo.entity.PostPhoto;
import com.ssafy.four_paws.post_photo.repository.PostPhotoRepository;
import com.ssafy.four_paws.user.entity.User;
import com.ssafy.four_paws.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostPhotoRepository postPhotoRepository;
    private final PostPostPhotoRepository postPostPhotoRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final CareZoneRepository careZoneRepository;

    // 게시글 전체 조회
    public List<PostResponseDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        if (posts.isEmpty()) {
            throw new CustomException("게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }

        return posts.stream()
                .map(this::mapToPostResponseDto)
                .collect(Collectors.toList());
    }

    // 게시글 상세 조회
    public Optional<PostResponseDto> getPostById(Long id) {
        return postRepository.findById(id).map(this::convertToDto);
    }

    // 게시글 상세 조회 Dto 매핑
    private PostResponseDto convertToDto(Post post) {
        PostResponseDto dto = new PostResponseDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setNickname(post.getUser().getNickname());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());

        if (post.getCareZone() != null) {
            dto.setCareZoneId(post.getCareZone().getId());
            dto.setCareZoneName(post.getCareZone().getName());
        }

        if (post.getUser() != null) {
            dto.setUserId(post.getUser().getId());
        }

        // 사진 URL 설정
        if (post.getPostPostPhoto() != null && !post.getPostPostPhoto().isEmpty()) {
            List<String> photoUrls = post.getPostPostPhoto().stream()
                    .map(ppp -> ppp.getPostPhoto().getPhotoUrl())
                    .collect(Collectors.toList());
            dto.setPhotoUrl(photoUrls);
        }

        return dto;
    }

    // 게시글 작성
    @Transactional
    public PostResponseDto createPost(PostCreateDto postCreateDto, User user) throws Exception {
        // 게시글 생성 및 설정
        Post post = new Post();
        post.setTitle(postCreateDto.getTitle());
        post.setContent(postCreateDto.getContent());
        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        // careZoneId 설정
        // 화면에서 기입한 carezoneid가 유효한지 확인함
        // -> 고정값 중 택1 하도록 할거라 유효값 검사 무의미?
        if (postCreateDto.getCareZoneId() != null) {
            CareZone careZone = careZoneRepository.findById(postCreateDto.getCareZoneId())
                    .orElseThrow(() -> new CustomException("유효하지 않은 careZoneId입니다.", HttpStatus.BAD_REQUEST));
            post.setCareZone(careZone);
        }

        // 게시글 저장
        Post savedPost = postRepository.save(post);

        // 사진 업로드 및 조인 엔티티 생성
        if (postCreateDto.getPhotos() != null && !postCreateDto.getPhotos().isEmpty()) {
            Set<PostPostPhoto> postPostPhotoSet = postCreateDto.getPhotos().stream().map(photo -> {
                try {
                    // 파일 저장
                    String photoUrl = fileStorageService.saveFile(photo);

                    // PostPhoto 엔티티 생성 및 저장
                    PostPhoto postPhoto = new PostPhoto();
                    postPhoto.setPhotoUrl(photoUrl);
                    postPhoto.setCreatedAt(LocalDateTime.now());
                    postPhotoRepository.save(postPhoto); // 먼저 저장하여 ID를 생성

                    // 조인 엔티티 생성
                    PostPostPhoto postPostPhotos = new PostPostPhoto();
                    postPostPhotos.setPostPostPhotoId(new PostPostPhotoId(savedPost.getId(), postPhoto.getId()));
                    postPostPhotos.setPost(savedPost);
                    postPostPhotos.setPostPhoto(postPhoto);

                    return postPostPhotos;
                } catch (IOException e) {
                    throw new RuntimeException("사진 업로드에 실패했습니다.", e);
                }
            }).collect(Collectors.toSet());

            // 조인 엔티티 저장
            postPostPhotoRepository.saveAll(postPostPhotoSet);
        }

        // 응답 DTO 매핑
        return mapToPostResponseDto(savedPost);
    }

    // 게시글 응답 DTO 매핑
    private PostResponseDto mapToPostResponseDto(Post post) {
        PostResponseDto dto = new PostResponseDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setNickname(post.getUser().getNickname());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());

        // photoUrl 목록 생성
        List<String> photoUrls = post.getPostPostPhoto().stream()
                .map(ppp -> ppp.getPostPhoto().getPhotoUrl())
                .collect(Collectors.toList());

        dto.setPhotoUrl(photoUrls);

        // 추가적인 필드 설정 (옵션)
        if (post.getUser() != null) {
            dto.setUserId(post.getUser().getId());
        }

        if (post.getCareZone() != null) {
            dto.setCareZoneId(post.getCareZone().getId());
            dto.setCareZoneName(post.getCareZone().getName());
        }

        return dto;
    }

}
