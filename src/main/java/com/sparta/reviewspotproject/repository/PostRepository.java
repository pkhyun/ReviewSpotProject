package com.sparta.reviewspotproject.repository;

import com.sparta.reviewspotproject.entity.Post;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

  Page<Post> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

  Page<Post> findAllByOrderByPostLikesCountDesc(Pageable pageable);


}

