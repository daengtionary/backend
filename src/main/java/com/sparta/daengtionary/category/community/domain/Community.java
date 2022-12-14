package com.sparta.daengtionary.category.community.domain;

import com.sparta.daengtionary.aop.exception.CustomException;
import com.sparta.daengtionary.aop.exception.ErrorCode;
import com.sparta.daengtionary.aop.util.Timestamped;
import com.sparta.daengtionary.category.community.dto.request.CommunityRequestDto;
import com.sparta.daengtionary.category.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Community extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long communityNo;
    @JoinColumn(name = "memberNo", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @Column
    private int view;
    @OneToMany(mappedBy = "community")
    List<CommunityImg> communityImgs;


    @Builder
    public Community(Long communityNo, Member member, String category, String title, String content) {
        this.communityNo = communityNo;
        this.member = member;
        this.category = category;
        this.title = title;
        this.content = content;
        this.view = 0;
    }

    public void updateCommunity(CommunityRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.category = requestDto.getCategory();
        this.content = requestDto.getContent();
    }

    public void viewUpdate() {
        this.view += 1;
    }

    public void validateMember(Member member) {
        if (!this.member.equals(member)) {
            throw new CustomException(ErrorCode.MAP_WRONG_ACCESS);
        }
    }
}