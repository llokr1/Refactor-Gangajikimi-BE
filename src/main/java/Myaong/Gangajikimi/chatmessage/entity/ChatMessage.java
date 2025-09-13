package Myaong.Gangajikimi.chatmessage.entity;

import Myaong.Gangajikimi.chatroom.entity.ChatRoom;
import Myaong.Gangajikimi.common.BaseEntity;
import Myaong.Gangajikimi.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ChatMessage extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id", nullable = false)
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private Member sender;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
}
