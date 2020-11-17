package com.javaproject.harang.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_TOKEN(401,"Invalid Token"),
    USER_NOT_FOUND(404, "User Not Found"),
    ADMIN_NOT_FOUND(404, "Admin Not Found"),
    TARGET_NOT_FOUND(404, "Target Not Found"),
    POST_NOT_FOUND(404, "Post Not Found"),
    WRITER_NOT_FOUND(404, "Writer Not Found"),
    APPLICATION_NOT_FOUND(404, "Application Not Found"),
    APPLICATION_ALREADY_EXCEPTION(409, "Application Already Exception"),
    REPORT_ALREADY_EXCEPTION(409, "Report Already Exception"),
    USER_ALREADY_EXCEPTION(409, "User Already Exception"),
    USER_ALREADY_REPORT(409, "User Already Report"),
    MEMBER_ALREADY_INCLUDE_EXCEPTION(409, "Member Already Include Exception");

    private final int status;

    private final String message;
}
