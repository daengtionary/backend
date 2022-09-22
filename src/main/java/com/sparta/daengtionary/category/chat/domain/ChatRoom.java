package com.sparta.daengtionary.category.chat.domain;

import com.sparta.daengtionary.category.member.domain.Member;
import com.sparta.daengtionary.aop.util.Timestamped;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class ChatRoom extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomNo;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String type;

    @JoinColumn(name = "creatorNo")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member creator;

    @JoinColumn(name = "targetNo")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member target;

    @OneToMany(mappedBy = "chatRoom")
    private List<ChatRoomMember> chatRoomMembers;


    public ChatRoom() {

    }

    public static ChatRoom createChatPersonalRoom(Member creator, Member target) {
        ChatRoom chatRoom = new ChatRoom();

        chatRoom.title = "personal";
        chatRoom.type = "personal";
        chatRoom.creator = creator;
        chatRoom.target = target;

        return chatRoom;
    }
}