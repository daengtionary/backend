package com.sparta.daengtionary.category.trade.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class TradeImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tradeImgNo;

    @JoinColumn(name = "tradeNo", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Trade trade;

    @Column
    private String tradeImg;

    @Builder
    public TradeImg(Long tradeImgNo, Trade trade, String tradeImg) {
        this.tradeImgNo = tradeImgNo;
        this.trade = trade;
        this.tradeImg = tradeImg;
    }
}