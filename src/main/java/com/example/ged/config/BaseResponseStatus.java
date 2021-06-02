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
    EMPTY_ACCESS_TOKEN(false,2002,"ACCESS TOKEN을 입력하세요."),
    EMPTY_DEVICE_TOKEN(false,2003,"DEVICE TOKEN을 입력하세요."),
    EMPTY_USER_JOB(false,2004,"직군을 입력하세요."),
    INVALID_USER_JOB(false,2005,"유효하지 않은 직군입니다. [기획자, 개발자, 디자이너] 중에 입력하세요"),

    // 3000 : Response 오류
    FAILED_TO_GET_USER_JOB_CATEGORIES(false,3001,"직군 카테고리 조회에 실패하였습니다."),
    NOT_FOUND_USER(false,3002,"존재하지 않는 회원입니다."),






    // 4000 : Database 오류
    SERVER_ERROR(false, 4000, "서버와의 통신에 실패하였습니다."),
    DATABASE_ERROR(false, 4001, "데이터베이스 연결에 실패하였습니다."),

    // 5000 : 기타 오류
    WRONG_URL(false,5001,"잘못된 URL 정보입니다."),
    FAILED_TO_CONNECT(false,5002,"URL 연결에 실패했습니다."),
    FAILED_TO_READ_RESPONSE(false,5003,"로그인 정보 조회에 실패했습니다."),
    FAILED_TO_PARSE(false,5004,"파싱에 실패했습니다."),
    FORBIDDEN_ACCESS(false, 5005, "접근 권한이 없습니다."),
    FAILED_TO_KAKAO_SIGN_UP(false, 5006, "카카오 회원가입에 실패하였습니다."),
    FAILED_TO_NAVER_SIGN_UP(false, 5007, "네이버 회원가입에 실패하였습니다."),
    FAILED_TO_KAKAO_SIGN_IN(false, 5008, "카카오 로그인에 실패하였습니다."),
    FAILED_TO_NAVER_SIGN_IN(false, 5009, "네이버 로그인에 실패하였습니다."),
    EXIST_USER(false, 5010, "존재하는 회원입니다. 로그인을 시도하세요"),
    ;


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
