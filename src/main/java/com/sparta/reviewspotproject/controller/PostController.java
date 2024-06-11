package com.sparta.reviewspotproject.controller;

import com.sparta.reviewspotproject.dto.PostRequestDto;
import com.sparta.reviewspotproject.dto.PostResponseDto;
import com.sparta.reviewspotproject.security.UserDetailsImpl;
import com.sparta.reviewspotproject.service.PostService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

  private final PostService postService;

  @PostMapping("/posts")
  public PostResponseDto createPost(@Valid @RequestBody PostRequestDto postRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return postService.createPost(postRequestDto, userDetails.getUser());

  }

  @GetMapping("/posts/{postId}")
  public PostResponseDto getPost(@PathVariable Long postId) {

    return postService.getPost(postId);
  }

//  @GetMapping("/posts")
//  public List<PostResponseDto> getPosts() {
//    return postService.getPosts();
//  }


  // 생성일자 최신순 페이징처리
  @GetMapping("/posts/paging")
  public Page<PostResponseDto> getPostAll(
      @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
    return postService.getPostAll(pageable);
  }

  // 좋아요 최신순 페이징 처리
  @GetMapping("/posts/bylikescount/paging")
  public Page<PostResponseDto> getPostByLikesCount(
      @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
    return postService.getPostsByPostLikesCount(pageable);
  }

  // 기간별 검색 페이징 처리
  @GetMapping("/posts/search/{start}/{end}")
  public Page<PostResponseDto> searchPost(
      @PathVariable LocalDate start,
      @PathVariable LocalDate end,
      @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
    LocalDateTime startDate = start.atStartOfDay(); // LocalDate -> LocalDateTime 변환
    LocalDateTime endDate = end.atTime(23, 59, 59);
    return postService.searchPost(startDate, endDate, pageable);
  }


  @PutMapping("/posts/{postId}")
  public PostResponseDto updatePost(@PathVariable Long postId,
      @Valid @RequestBody PostResponseDto postRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return postService.update(postId, postRequestDto, userDetails.getUser());
  }

  @DeleteMapping("/posts/{postId}")
  public ResponseEntity<String> deletePost(@PathVariable Long postId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    postService.delete(postId, userDetails.getUser());
    return new ResponseEntity<>("댓글이 성공적으로 삭제되었습니다.", HttpStatus.OK);
  }
}
