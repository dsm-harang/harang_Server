package com.javaproject.harang.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class UserReportResponse {
    private Integer id;
    private Integer targetId;
    private String targetUserId;
    private String targetName;
    private LocalDate reportTime;
}
