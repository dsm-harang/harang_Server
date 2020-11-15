package com.javaproject.harang.payload.response;

import com.javaproject.harang.entity.score.Score;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ScoreResponse {
    private List<Score> scores;


}
