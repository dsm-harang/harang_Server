package com.javaproject.harang.payload.response;

import com.javaproject.harang.entity.notify.Notify;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class NotifyResponse {
    List<Notify> notify;
}

