package com.sparta.reviewspotproject.dto;

import com.sparta.reviewspotproject.entity.Post;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto {

  private Long postId;
  private Long userId;
  private String title;
  private String contents;
  private LocalDateTime createAt;
  private LocalDateTime modifiedAt;
  private int postLikesCount;

  public PostResponseDto(Post post) {
    this.postId = post.getId();
    this.userId = post.getUser().getId();
    this.title = post.getTitle();
    this.contents = post.getContents();
    this.createAt = post.getCreatedAt();
    this.modifiedAt = post.getModifiedAt();
    this.postLikesCount = post.getPostLikesCount();
  }

  public static PostResponseDto fromEntity(Post post) {
    PostResponseDto postResponseDto = new PostResponseDto();
    postResponseDto.postId = post.getId();
    postResponseDto.userId = post.getUser().getId();
    postResponseDto.title = post.getTitle();
    postResponseDto.contents = post.getContents();
    postResponseDto.createAt = post.getCreatedAt();
    postResponseDto.modifiedAt = post.getModifiedAt();
    postResponseDto.postLikesCount = post.getPostLikesCount();

    return postResponseDto;
  }

}