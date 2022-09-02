package com.sparta.daengtionary.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseBodyDto {
    @Getter
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private static class Body {
        private Integer state;
        private Object data;
        private String message;
    }

    public ResponseEntity<?> success(Object data, String massage) {
        Body body = Body.builder()
                .state(HttpStatus.OK.value())
                .data(data)
                .message(massage)
                .build();

        return ResponseEntity.ok(body);
    }

    // 메시지만 있는 성공 응답
    public ResponseEntity<?> success(String massage) {
        Body body = Body.builder()
                .state(HttpStatus.OK.value())
                .message(massage)
                .build();

        return ResponseEntity.ok(body);
    }
}