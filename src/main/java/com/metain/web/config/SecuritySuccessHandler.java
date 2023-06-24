package com.metain.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class SecuritySuccessHandler  extends SavedRequestAwareAuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        //로그인 성공시 필요한 작업 추가
        logger.info("AuthenticationSuccessHandler : 로그인 성공 request",request);
        logger.info("AuthenticationSuccessHandler : 로그인 성공 response",response);
        logger.info("AuthenticationSuccessHandler : 로그인 성공 authentication",authentication);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
