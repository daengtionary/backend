package com.sparta.daengtionary.domain.community;

import com.sparta.daengtionary.configration.error.CustomException;
import com.sparta.daengtionary.configration.error.ErrorCode;
import com.sparta.daengtionary.domain.Member;
import com.sparta.daengtionary.dto.request.CommunityRequestDto;
import com.sparta.daengtionary.util.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Community extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long communityNo;

    @JoinColumn(name = "mumber_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column
    private int view;

    @Builder
    public Community(Long communityNo, Member member, String title, String content) {
        this.communityNo = communityNo;
        this.member = member;
        this.title = title;
        this.content = content;
        this.view = 0;
    }

    public void updateCommunity(CommunityRequestDto requestDto){
        this.title = requestDto.getTitle();
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