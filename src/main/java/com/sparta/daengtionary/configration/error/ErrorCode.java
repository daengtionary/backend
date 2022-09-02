package com.sparta.daengtionary.configration.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    OK(HttpStatus.OK.value(),"OK","true"),

    //문자열 체크
    NOT_VALIDCONTENT(HttpStatus.BAD_REQUEST.value(),"BAD_REQUEST","유효하지 않는 내용입니다."),
    NOT_VALIDURL(HttpStatus.BAD_REQUEST.value(),"BAD_REQUEST","유효하지 않는 URL 입니다."),

    //회원가입
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST.value(), "DUPLICATE_EMAIL", "이미 사용중인 email 입니다."),
    DUPLICATE_NICK(HttpStatus.BAD_REQUEST.value(), "DUPLICATE_NICK", "이미 사용중인 닉네임 입니다."),
    WRONG_ADMINCODE(HttpStatus.BAD_REQUEST.value(), "WRONG_ADMINCODE", "관리자 코드가 일치하지 않습니다."),


    //TOKEN


    //로그인
    WRONG_EMAIL(HttpStatus.BAD_REQUEST.value(), "WRONG_EMAIL", "존재하지 않는 email 입니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST.value(), "WRONG_PASSWORD", "비밀번호가 일치하지 않습니다."),

    //회원탈퇴


    //기타
    NOT_FOUND_USER_INFO(HttpStatus.NOT_FOUND.value(), "NOT_FOUND", "해당 유저가 존재하지 않습니다"),
    WRONG_INPUT_CONTENT(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),"UNSUPPORTED_MEDIA_TYPE","업로드 타입이 잘못되었습니다"),


    //게시글
    MAP_NOT_FOUND(HttpStatus.NOT_FOUND.value(),"NOT_FOUND","해당 게시물을 찾을 수 없습니다."),
    MAP_DUPLICATE_TITLE(HttpStatus.BAD_REQUEST.value(),"BAD_REQUEST","중복된 제목입니다."),
    MAP_UPDATE_WRONG_ACCESS(HttpStatus.BAD_REQUEST.value(),"BAD_REQUEST","본인의 게시물만 수정할 수 있습니다."),
    MAP_DELETE_WRONG_ACCESS(HttpStatus.BAD_REQUEST.value(),"BAD_REQUEST","본인의 게시물만 삭제할 수 있습니다."),
    MAP_WRONG_INPUT(HttpStatus.BAD_REQUEST.value(),"BAD_REQUEST","비어있는 항목을 채워주세요"),

    //댓글

    //챗팅

    //이미지
    WRONG_INPUT_IMAGE(HttpStatus.BAD_REQUEST.value(),"BAD_REQUEST","이미지는 반드시 있어야 됩니다."),
    IMAGE_UPLOAD_ERROR(HttpStatus.BAD_REQUEST.value(),"BAD_REQUEST","이미지 업로드에 실패했습니다"),
    WRONG_IMAGE_FORMAT(HttpStatus.BAD_REQUEST.value(),"BAD_REQUEST","지원하지 않는 파일 형식입니다.");



    private final Integer status;
    private final String code;
    private final String message;

}
