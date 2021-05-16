package com.example.ged.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    // 1000 : 요청 성공
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    // 2000 : Request 오류
    EMPTY_JWT(false,2000,"JWT를 입력해주세요."),
    INVALID_JWT(false,2001,"유효하지 않은 JWT 입니다."),

    // 3000 : Response 오류
    FAILED_TO_GET_USER_JOB_CATEGORIES(false,3001,"직군 카테고리 조회에 실패하였습니다."),
    NOT_FOUND_USER(false,3002,"존재자하지 않는 회원입니다."),
    WRONG_URL(false,3003,"잘못된 URL 정보입니다."),
    FAILED_TO_CONNECT(false,3004,"URL 연결에 실패했습니다."),
    FAILED_TO_READ_RESPONSE(false,3005,"로그인 정보 조회에 실패했습니다."),
    FAILED_TO_PARSE(false,3006,"파싱에 실패했습니다."),


    // 4000 : Database 오류
    SERVER_ERROR(false, 4000, "서버와의 통신에 실패하였습니다."),
    DATABASE_ERROR(false, 4001, "데이터베이스 연결에 실패하였습니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
