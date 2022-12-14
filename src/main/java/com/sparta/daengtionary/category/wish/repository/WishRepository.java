package com.sparta.daengtionary.category.wish.repository;

import com.sparta.daengtionary.category.member.domain.Member;
import com.sparta.daengtionary.category.trade.domain.Trade;
import com.sparta.daengtionary.category.wish.domain.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {
    List<Wish> findAllByTrade(Trade trade);

    Wish findByMapAndMember(Object map, Member member);

    Wish findByCommunityAndMember(Object com, Member member);

    Wish findByTradeAndMember(Object trade, Member member);

}
