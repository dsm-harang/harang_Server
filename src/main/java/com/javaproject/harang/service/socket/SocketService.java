package com.javaproject.harang.service.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.javaproject.harang.payload.request.MessageRequest;

public interface SocketService {
    void connect(SocketIOClient client);
    void disConnect(SocketIOClient client);
    void joinRoom(SocketIOClient client, String room);
    void chat(SocketIOClient client, MessageRequest messageRequest);
}
