package com.javaproject.harang.payload.response;

import com.javaproject.harang.entity.message.enumMessage.RoomStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageRoomResponse {
    private Integer postId;
    private Integer roomId;
    private RoomStatus roomStatus;
    private String roomTitle;
}

