package com.javaproject.harang.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_TOKEN(401,"Invalid Token"),
    PERMISSION_DENIED_EXCEPTION(401,"Permission Denied"),
    USER_NOT_FOUND(404, "User Not Found"),
    ADMIN_NOT_FOUND(404, "Admin Not Found"),
    TARGET_NOT_FOUND(404, "Target Not Found"),
    NOTIFY_NOT_FOUND(404,"Notify Not Found"),
    POST_NOT_FOUND(404, "Post Not Found"),
    WRITER_NOT_FOUND(404, "Writer Not Found"),
    CHATROOM_NOT_FOUND(404,"ChatRoom Not Found"),
    CHATROOM_JOIN_NOT_FOUND(404,"ChatRoomJoin Not Found"),
    MEMBER_NOT_FOUND(404,"Member Not Found"),
    SCORE_NOT_FOUND(404,"Score Not Found"),
    APPLICATION_NOT_FOUND(404, "Application Not Found"),
    NOT_EQUALS_USER(404,"Not Equals User"),
    ROOM_CLOSE_EXCEPTION(404,"Room Close Exception"),
    TOKEN_NOT_FOUND(404,"Token Not Found"),
    ALREADY_TARGET_EXCEPTION(409, "Already Target Exception"),
    APPLICATION_ALREADY_EXCEPTION(409, "Application Already Exception"),
    REPORT_ALREADY_EXCEPTION(409, "Report Already Exception"),
    USER_ALREADY_EXCEPTION(409, "User Already Exception"),
    ALL_READY_SCORE(409,"Score Already Exception"),
    USER_ALREADY_REPORT(409, "User Already Report"),
    MEMBER_ALREADY_INCLUDE_EXCEPTION(409, "Member Already Include Exception");

    private final int status;

    private final String message;
}
