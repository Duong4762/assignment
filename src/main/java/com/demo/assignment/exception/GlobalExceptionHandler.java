package com.demo.assignment.exception;

import com.demo.assignment.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(AppException.class)
    ResponseDto handleAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error(errorCode.getMessage(), e);
        return ResponseDto.builder()
                .statusCode(errorCode.getCode())
                .message(errorCode.getMessage())
                .content(errorCode.getContent())
                .dateTime(LocalDateTime.now().toString())
                .build();
    }
}
