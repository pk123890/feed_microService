package com.example.feed.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
public class BaseResponse<T>
{
    private Boolean success;
    private String  errorMessage;
    private T data;
    private HttpStatus httpStatus;
}
