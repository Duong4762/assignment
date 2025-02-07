package com.demo.assignment.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_EXISTED(400, "Thất bại", "Tài khoản đã tồn tại"),
    LOGIN_FAILED(404, "Không tìm thấy tài nguyên", "Tài khoản hoặc mật khẩu không đúng!"),
    NOT_FOUND(400, "Không tìm thấy tài nguyên", ""),
    DELETE_FILM_FAILED(400, "Xóa phim thất bại", "Phim đã có lịch chiếu"),
    ADD_FILM_FAILED(500, "Dữ liệu không hợp lệ", ""),
    CANNOT_CREATE_SCHEDULE(400, "Lịch chiếu bị trùng", "Lịch chiếu bị trùng"),
    ;
    private int code;
    private String message;
    private String content;
    ErrorCode(int code, String message, String content){
        this.code = code;
        this.message = message;
        this.content = content;
    }
}
