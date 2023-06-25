
package com.metain.web.controller;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/error")
public class ExceptionController implements ErrorController {

    @RequestMapping
    public String handleError(HttpServletRequest request, HttpServletResponse response) {
        // 오류 상태 코드 가져오기
        int statusCode = response.getStatus();

        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            // 404 오류 페이지를 반환
            return "error/404";
        } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            // 500 오류 페이지를 반환
            return "error/500";
        }
        else if (statusCode == HttpStatus.FORBIDDEN.value()){
            // 403 오류 페이지를 반환
            return "error/403";
        }

        // 기본 오류 페이지를 반환
        return "error/500";
    }

    @RequestMapping("/access-denied")
    public String handleAccessDeniedError() {
        return "error/403"; // 접근 거부 페이지로 이동
    }


}
