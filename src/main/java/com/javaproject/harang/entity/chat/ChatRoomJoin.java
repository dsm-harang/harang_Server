package com.javaproject.harang.entity.chat;

import com.javaproject.harang.entity.user.customer.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatRoomJoin {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name =  "user_id", nullable = false,insertable = false, updatable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false,insertable = false, updatable = false)
    private ChatRoom chatRoom;

    public ChatRoomJoin(Customer customer , ChatRoom chatRoom){
        this.customer=customer;
        this.chatRoom=chatRoom;
    }
}
