package com.ssafy.four_paws.post.controller;

import com.ssafy.four_paws.exception.CustomException;
import com.ssafy.four_paws.post.dto.PostCreateDto;
import com.ssafy.four_paws.post.dto.PostResponseDto;
import com.ssafy.four_paws.post.service.PostService;
import com.ssafy.four_paws.user.entity.User;
import com.ssafy.four_paws.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;

    // 게시글 전체 조회(목록 조회)
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        List<PostResponseDto> posts = postService.getAllPosts();

        return ResponseEntity.ok(posts);
    }

    // 게시글 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id) {
        Optional<PostResponseDto> post = postService.getPostById(id);

        return postService.getPostById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 게시글 작성(사진 여러장)
    @PostMapping("/write")
    public ResponseEntity<PostResponseDto> createPost(
            @Valid @ModelAttribute PostCreateDto postCreateDto,
                                   Authentication authentication) throws Exception {
        // 로그인 상태인지 확인
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                                  .orElseThrow(() -> new CustomException("로그인 후 이용 가능합니다.", HttpStatus.UNAUTHORIZED));

        // 로그 추가
        System.out.println("Title: " + postCreateDto.getTitle());
        System.out.println("Content: " + postCreateDto.getContent());

        PostResponseDto createdPost = postService.createPost(postCreateDto, user);
        return ResponseEntity.ok(createdPost);
    }

    // 게시글 수정

    // 게시글 삭제


}
