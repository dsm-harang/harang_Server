package com.javaproject.harang.controller;

import com.javaproject.harang.payload.response.NotifyResponse;
import com.javaproject.harang.service.notice.NotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NotifyService notifyService;

    @GetMapping()
    public NotifyResponse MyNotify() {
        return notifyService.MyNotify();
    }

}