package com.sparta.reviewspotproject.entity;

import com.sparta.reviewspotproject.dto.PostRequestDto;
import com.sparta.reviewspotproject.dto.PostResponseDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@Setter
@Table(name = "post")
@NoArgsConstructor
public class Post extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = false)
  private String title;

  @Column(nullable = false, unique = false)
  private String contents;

  @CreatedDate
  @Column(updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime createdAt = LocalDateTime.now();

  @LastModifiedDate
  @Column
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime modifiedAt = LocalDateTime.now();

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column
  private int postLikesCount;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments;

  public Post(PostRequestDto postRequestDto) {
    this.contents = postRequestDto.getContents();
    this.title = postRequestDto.getTitle();
  }

  public Post(PostRequestDto postRequestDto, User user) {
    this.contents = postRequestDto.getContents();
    this.title = postRequestDto.getTitle();
    this.user = user;
    this.postLikesCount = 0;
  }

  public void update(PostResponseDto postRequestDto, User user) {
    this.title = postRequestDto.getTitle();
    this.contents = postRequestDto.getContents();
    this.user = user;
  }


}
