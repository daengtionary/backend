package com.sparta.daengtionary.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.daengtionary.dto.request.DogRequestDto;
import com.sparta.daengtionary.util.Gender;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dogNo;
    @JoinColumn(name = "memberNo", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Member member;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String breed;
    @Column(nullable = false)
    private Gender gender;
    @Column(nullable = false)
    private Float weight;
    @Column
    private String image;


    public Dog() {

    }

    @Builder
    public Dog(Long dogNo, Member member, String name, String breed, Gender gender, Float weight, String image) {
        this.dogNo = dogNo;
        this.member = member;
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.weight = weight;
        this.image = image;
    }

    public void update(DogRequestDto requestDto) {
        this.name = requestDto.getName();
        this.breed = requestDto.getBreed();
        this.gender = requestDto.getGender();
        this.weight = requestDto.getWeight();
        this.image = requestDto.getImage();
    }
}