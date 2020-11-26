package com.javaproject.harang.entity.message;

import com.javaproject.harang.entity.message.enumMessage.RoomStatus;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer postId;

    private RoomStatus roomStatus;

    public MessageRoom updateRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;

        return this;
    }
}
