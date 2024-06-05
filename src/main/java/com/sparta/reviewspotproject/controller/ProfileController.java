package com.sparta.reviewspotproject.controller;

import com.sparta.reviewspotproject.dto.ProfileRequestDto;
import com.sparta.reviewspotproject.dto.ProfileResponseDto;
import com.sparta.reviewspotproject.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    // 사용자의 프로필 조회
    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponseDto> getProfile(@PathVariable Long id) {
        ProfileResponseDto responseDto = profileService.getProfile(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    // 사용자의 프로필 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProfile(@PathVariable Long id, @RequestBody @Valid ProfileRequestDto requestDto) {
        profileService.updateProfile(id, requestDto);
        return new ResponseEntity<>("프로필이 성공적으로 수정되었습니다.", HttpStatus.OK);
    }

//    // 사용자의 비밀번호 확인(본인확인)
//    @PostMapping("/{id}")
//    public ResponseEntity<String> checkPassword(@PathVariable Long id, @RequestBody @Valid ProfileRequestDto requestDto) {
//        return new ResponseEntity<>("사용자 확인이 완료되었습니다.", HttpStatus.OK);
//    }
}