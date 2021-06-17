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
    INVALID_USER_JOB(false,2005,"유효하지 않은 직군입니다. [기획자, 개발자, 디자이너] 중에 입력하세요."),
    EMPTY_USER_NAME(false,2006,"유저 이름을 입력하세요."),
    INVALID_INTRODUCE_LENGTH(false,2007,"소개글은 40자 이하여야 합니다."),
    EMPTY_IS_MEMBERS(false,2008,"멤버스 여부를 입력하세요."),
    INVALID_IS_MEMBERS(false,2009,"멤버스 여부는 [Y, N] 중에 입력하세요."),
    EMPTY_INTRODUCE(false,2010,"소개글을 입력하세요."),
    EMPTY_PROFILE_IMAGE_URL(false,2011,"프로필 이미지를 입력하세요."),
    EMPTY_BACKGROUND_IMAGE_URL(false,2012,"배경 이미지를 입력하세요."),
    EMPTY_TYPE(false,2013,"타입을 입력하세요."),
    INVALID_TYPE(false,2014,"유효하지않은 타입입니다. [1,2,3,4] 중에 입력하세요."),
    INVALID_REFERENCEIDX(false,2015,"유효하지 않은 referenceIdx입니다."),
    EMPTY_REFERENCEIDX(false,2016,"referenceIdx를 입력하세요."),
    EMPTY_ID_TOKEN(false,2017,"ID token을 입력하세요."),
    EMPTY_USER_EMAIL(false,2018,"email을 입력하세요."),

    // 3000 : Response 오류
    FAILED_TO_GET_USER_JOB_CATEGORIES(false,3001,"직군 카테고리 조회에 실패하였습니다."),
    NOT_FOUND_USER(false,3002,"존재하지 않는 회원입니다."),
    FAILED_TO_SAVE_USERINFO(false,3003,"UserInfo를 저장할 수 없습니다."),
    FAILED_TO_FIND_BY_USERIDX_AND_STATUS(false,3004,"userIdx와 status로 UserInfo 조회에 실패했습니다."),
    FAILED_TO_FIND_BY_SOCIALID_AND_STATUS(false,3005,"socialId와 status로 UserInfo 조회에 실패했습니다."),
    FAILED_TO_FIND_BY_USERJOB_AND_ISMEMBERS_AND_STATUS(false, 3006, "직군,멤버스여부, 상태로 UserInfo 조회에 실패했습니다."),
    FAILED_TO_FIND_BY_ID(false, 3007, "id로 ReferenceCategory 조회에 실패했습니다."),
    NOT_FOUND_REFERENCE_CATEGORY(false, 3008, "레퍼런스 카테고리를 찾을 수 없습니다."),
    FAILED_TO_FIND_BY_REFERENCE_CATEGORY_AND_STATUS(false, 3009, "referenceCategory와 status로 Reference 조회에 실패했습니다."),
    FAILED_TO_FIND_BY_REFERENCEIDX_AND_STATUS(false, 3010, "referenceIdx와 status로 Reference 조회에 실패했습니다."),
    FAILED_TO_EXIST_BY_REFERENCEIDX_AND_STATUS(false, 3011, "referenceIdx와 status로 Reference 존재 여부 확인에 실패했습니다."),
    NOT_FOUND_REFERENCE(false, 3012, "레퍼런스를 찾을 수 없습니다."),
    FAILED_TO_EXIST_BY_USERINFO_AND_REFERENCE_AND_STATUS(false, 3013, "userInfo,reference,status로 ReferenceHeart 존재 여부 확인에 실패했습니다."),
    FAILED_TO_SAVE_REFERENCE_HEART(false,3014,"ReferenceHeart를 저장할 수 없습니다."),
    FAILED_TO_FIND_BY_USERINFO_AND_REFERENCE_AND_STATUS(false, 3015, "userInfo,reference,status로 ReferenceHeart 조회에 실패했습니다."),
    FAILED_TO_FIND_USERINFO_AND_STATUS(false, 3016, "userInfo, status로 ReferenceHeart 조회에 실패했습니다."),
    FAILED_TO_FIND_BY_USERINFO_AND_STATUS(false, 3016, "userInfo, status로 UserSns 조회에 실패했습니다."),

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
    FORBIDDEN_USER(false, 5011, "해당 회원에 접근할 수 없습니다."),

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
