package com.javaproject.harang.payload.response;

import com.javaproject.harang.entity.member.Member;
import com.javaproject.harang.entity.member.MyPostForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ListScoreResponse {
    List<Member> ListScore  ;

}
