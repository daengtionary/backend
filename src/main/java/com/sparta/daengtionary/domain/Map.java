package com.sparta.daengtionary.domain;

import com.sparta.daengtionary.util.Timestamped;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Map extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mapId;

    @JoinColumn(name = "member_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String content;

    @Column
    private float star;

    @Column
    private int view;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private int mapx;

    @Column(nullable = false)
    private int mapy;

    public Map(){

    }

    @Builder
    public Map(Long mapId,Member member,String title,String category,String content,
               String address,int mapx,int mapy){
        this.mapId = mapId;
        this.member = member;
        this.title = title;
        this.category = category;
        this.content = content;
        this.star = 0;
        this.view = 0;
        this.address = address;
        this.mapx = mapx;
        this.mapy = mapy;
    }

    public void UpdateMap(){

    }

    public void viewUpdate(int view){
        this.view = view;
    }

    public void starUpdate(float star){
        this.star = star;
    }

}
