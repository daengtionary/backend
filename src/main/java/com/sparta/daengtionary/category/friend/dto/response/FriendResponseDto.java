package com.sparta.daengtionary.category.friend.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.daengtionary.category.friend.domain.FriendImg;
import com.sparta.daengtionary.category.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class FriendResponseDto {
    private Long friendNo;
    private String address;
    private String category;
    private String title;
    private String content;
    private String status;
    private int maxCount;
    private int count;
    private String image;
    @JsonFormat(pattern = "yyyy년 MM월 dd일 E요일 a hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy년 MM월 dd일 E요일 a hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;


    @Builder
    public FriendResponseDto(Long friendNo, String address, String category, String title, String content,
                             String status, int count, int maxCount, String image, LocalDateTime createdAt,
                             LocalDateTime modifiedAt) {
        this.friendNo = friendNo;
        this.address = address;
        this.category = category;
        this.title = title;
        this.content = content;
        this.status = status;
        this.count = count;
        this.maxCount = maxCount;
        this.image = image;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}