package com.javaproject.harang.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MySeePageResponse {
    List<MyPostResponse> MySeePage  ;
}
