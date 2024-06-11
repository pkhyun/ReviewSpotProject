package com.sparta.reviewspotproject.service;

import com.sparta.reviewspotproject.dto.PostRequestDto;
import com.sparta.reviewspotproject.dto.PostResponseDto;
import com.sparta.reviewspotproject.entity.Post;
import com.sparta.reviewspotproject.entity.User;
import com.sparta.reviewspotproject.repository.PostRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;

  public PostResponseDto createPost(PostRequestDto postRequestDto,
      User user) {

    Post post = new Post(postRequestDto, user);
    Post savePost = postRepository.save(post);
    return new PostResponseDto(savePost);
  }

  public PostResponseDto getPost(Long id) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
    return new PostResponseDto(post);
  }

  // 생성일자 최신순 정렬 페이징 처리
  public Page<PostResponseDto> getPostAll(
      @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
    Page<Post> posts = postRepository.findAllByOrderByCreatedAtDesc(pageable);
    return posts.map(PostResponseDto::fromEntity);
  }

  // 좋아요 많은순 정렬 페이징 처리
  public Page<PostResponseDto> getPostsByPostLikesCount(
      @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
    Page<Post> postsByPostCountLikes = postRepository.findAllByOrderByPostLikesCountDesc(pageable);
    return postsByPostCountLikes.map(PostResponseDto::fromEntity);
  }

  // 기간별 검색 및 페이징
  public Page<PostResponseDto> searchPost(LocalDateTime start, LocalDateTime end,
      Pageable pageable) {
    Page<Post> searchedPosts = postRepository.findByCreatedAtBetween(start, end, pageable);
    return searchedPosts.map(PostResponseDto::fromEntity);
  }


  @Transactional
  public PostResponseDto update(Long postId, PostResponseDto postRequestDto, User user) {
    findPost(postId);
    Post post = postRepository.findById(postId).get();

    if (post.getUser().getId() != user.getId()) {
      throw new IllegalArgumentException("게시물 작성자가 아니므로 수정할 수 없습니다.");
    }

    post.update(postRequestDto, user);
    return new PostResponseDto(post);
  }

  public void delete(Long postId, User user) {

    findPost(postId);
    Post post = postRepository.findById(postId).get();
//        Post post = postRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

    if (post.getUser().getId() != user.getId()) {
      throw new IllegalArgumentException("게시물 작성자가 아니므로 삭제할 수 없습니다.");
    }
    postRepository.delete(post);
  }

  public Post findPost(Long id) {
    return postRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
  }


}