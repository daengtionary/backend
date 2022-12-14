package com.sparta.daengtionary.category.member.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class KakaoUserInfoDto {
    private Long kakaoId;
    private String email;
    private String nick;


    @Builder
    public KakaoUserInfoDto(Long kakaoId, String email, String nick) {
        this.kakaoId = kakaoId;
        this.email = email;
        this.nick = nick;
    }
}