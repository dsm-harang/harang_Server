package com.javaproject.harang.service.notice;

import com.javaproject.harang.payload.response.NotifyResponse;

import java.util.List;

public interface NotifyService {
    List<NotifyResponse> myNotify();
    void deleteNotify(Integer notifyId);

}
