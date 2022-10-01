package com.sparta.daengtionary.category.friend.repository;

import com.sparta.daengtionary.category.friend.domain.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    Optional<Friend> findByFriendNo(Long friendNo);
}
