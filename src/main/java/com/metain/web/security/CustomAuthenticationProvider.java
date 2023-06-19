package com.metain.web.security;

import com.metain.web.domain.PrincipalDetails;
import com.metain.web.mapper.HrMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/***
 * [Authentication을 수행하는 주체 : 사용자의 인증을 처리하는 과정]
 *
 * 인증에 성공한 사용자의 정보와 권한을
 * UsernamePasswordAuthenticationToken 객체에 담아 반환
 *      -> 인증된 사용자의 권한 정보를 Spring Security에서 활용
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final PrincipalService principalService;

    public CustomAuthenticationProvider(BCryptPasswordEncoder bCryptPasswordEncoder, PrincipalService principalService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.principalService = principalService;
    }
    @Autowired
    HrMapper hrMapper;




    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.info("[CustomAuthenticationProvider] 시작 ---------------------------------");
        logger.info("----authentication.getName() ::::: {}", authentication.getName());
        logger.info("---------------------------------");


        String empSabun = authentication.getName();
        System.out.println("authenticate ::::: empSabun : " + empSabun);
        String empPwd = (String) authentication.getCredentials();

        if(StringUtils.isEmpty(empSabun) || StringUtils.isEmpty(empPwd)){
            throw new BadCredentialsException("아이디와 비밀번호를 입력해주세요");

        }

        //사원 인증 로직 수행
        PrincipalDetails principalDetails = (PrincipalDetails) principalService.loadUserByUsername(empSabun);

        //아이디가 틀린 경우
        if (principalDetails == null) {
            throw new InternalAuthenticationServiceException("존재하지 않는 사원번호입니다");
        }


        String password = empPwd;//비밀번호
        if(!bCryptPasswordEncoder.matches(password, principalDetails.getPassword())){
            throw new BadCredentialsException("아이디 혹은 비밀번호를 다시 확인해주세요.");
        }

        logger.info("----principalDetails.getUsername ::::: {}", principalDetails.getUsername());
        logger.info("----principalDetails.getPassword ::::: {}", principalDetails.getPassword());





        //UsernamePasswordAuthenticationToken(Object principal, Object credentials, authorities)
        //UsernamePasswordAuthenticationToken는 Authentication의 자식객체
        //인증완료된 결과로 UsernamePasswordAuthenticationToken를 리턴한다.




            //여기가 문제야
        if (principalDetails.getAuthorities() == null) {
            throw new RuntimeException(empSabun + "아무 권한이 없습니다.");
        }

        //db에서 가지고 온 권한을 GrantedAuthority 로 변환해야함.
        List<SimpleGrantedAuthority> authList = new ArrayList<>();
        for (GrantedAuthority authority : principalDetails.getAuthorities()) {
            System.out.println(authority);
            authList.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }
        logger.info("----principalDetails.getAuthorities ::::: {}", principalDetails.getAuthorities());
            return new UsernamePasswordAuthenticationToken(principalDetails, principalDetails.getPassword(), principalDetails.getAuthorities());

    }

//    protected void clearAuthenticationAttributes(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if(session == null)
//            return;
//        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
//    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }



}
