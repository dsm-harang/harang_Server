package com.javaproject.harang.entity.chat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.javaproject.harang.entity.user.customer.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatMessage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Integer id;
    @Column(nullable = false)
    private String message;
    @Column(nullable = false)
    private LocalDateTime time;


    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    @JsonBackReference
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id",nullable = false)
    private Customer writer;

    public ChatMessage(String message, LocalDateTime time, ChatRoom chatRoom, Customer writer){
        this.message=message;
        this.time=time;
        this.chatRoom=chatRoom;
        this.writer=writer;
    }
    public ChatMessage(String message, LocalDateTime time ,Customer writer){
        this.message=message;
        this.time=time;
        this.writer=writer;
    }
}