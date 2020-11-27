package com.javaproject.harang.controller;

import com.javaproject.harang.payload.response.NotifyResponse;
import com.javaproject.harang.service.notice.NotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NotifyService notifyService;

    @GetMapping()
    public List<NotifyResponse> myNotify() {
        return notifyService.myNotify();
    }

    @DeleteMapping("/{notifyId}")
    public void deleteNotify(@PathVariable Integer notifyId) {
        notifyService.deleteNotify(notifyId);
    }
}
