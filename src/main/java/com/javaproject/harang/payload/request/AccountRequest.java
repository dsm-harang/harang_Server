package com.javaproject.harang.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {

    @NotEmpty
    private String userId;

    @NotEmpty
    private String password;

}
