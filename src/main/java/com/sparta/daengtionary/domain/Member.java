package com.sparta.daengtionary.domain;

import com.sparta.daengtionary.util.Authority;
import com.sparta.daengtionary.util.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Member extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNo;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String nick;
    @Column(nullable = false)
    private Authority role;
    @Column
    private Long kakaoId;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dog> dogs;


    public Member() {
    }

    @Builder
    public Member(Long memberNo, String email, String password,
                  String nick, Authority role, Long kakaoId) {
        this.memberNo = memberNo;
        this.email = email;
        this.password = password;
        this.nick = nick;
        this.role = role;
        this.kakaoId = kakaoId;
    }

    public void update(String nick) {
        this.nick = nick;
    }
}