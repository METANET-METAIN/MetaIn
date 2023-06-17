package com.metain.web.security;

import com.metain.web.domain.PrincipalDetails;
import com.metain.web.mapper.HrMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

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

        PrincipalDetails principalDetails = (PrincipalDetails) principalService
                .loadUserByUsername(empSabun);

        logger.info("----principalDetails.getUsername ::::: {}", principalDetails.getUsername());
        logger.info("----principalDetails.getPassword ::::: {}", principalDetails.getPassword());




        //UsernamePasswordAuthenticationToken(Object principal, Object credentials, authorities)
        //UsernamePasswordAuthenticationToken는 Authentication의 자식객체
        //인증완료된 결과로 UsernamePasswordAuthenticationToken를 리턴한다.

        String password = empPwd;//비밀번호
            if(!bCryptPasswordEncoder.matches(password, principalDetails.getPassword())){
                throw new BadCredentialsException("아이디 혹은 비밀번호를 다시 확인해주세요.");
            }


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



//
//
//        List<Emp> emp = hrMapper.login(empSabun);
//        logger.info("----CustomAuthenticationProvider emp :::: ", emp);
//
//        if(emp==null){// ID가 없는경우
//
//            throw new RuntimeException("아이디 혹은 비밀번호를 다시 확인해주세요.");//spring exception
//        }
//
//        String password = (String)authentication.getCredentials();//비밀번호
//        if(!password.equals(emp.get(0).getEmpPwd())) {
//            if(!bCryptPasswordEncoder.matches(password, emp.get(0).getEmpPwd())){
//                throw new RuntimeException("아이디 혹은 비밀번호를 다시 확인해주세요.");
//            }
//        }
//
//        PrincipalDetails principalDetails = (PrincipalDetails) principalService
//                .loadUserByUsername(authentication.getName());
//
//        logger.info("----principalDetails.getUsername ::::: {}", principalDetails.getUsername());
//        logger.info("----principalDetails.getPassword ::::: {}", principalDetails.getPassword());
//        logger.info("----principalDetails.getAuthorities ::::: {}", principalDetails.getAuthorities());
//
//
//
//        //db에서 가지고 온 권한을 GrantedAuthority 로 변환해야함.
//
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

//        // 직급에 따른 권한 부여
//        if (emp.getEmpGrade().equals("SW")) {
//            authorities.add(new SimpleGrantedAuthority(Role.SW.name()));
//        } else if (emp.getEmpGrade().equals("DR")) {
//            authorities.add(new SimpleGrantedAuthority(Role.DR.name()));
//        } else if (emp.getEmpGrade().equals("GJ")) {
//            authorities.add(new SimpleGrantedAuthority(Role.GJ.name()));
//        } else if (emp.getEmpGrade().equals("CJ")) {
//            authorities.add(new SimpleGrantedAuthority(Role.CJ.name()));
//        } else if (emp.getEmpGrade().equals("HR")) {
//            authorities.add(new SimpleGrantedAuthority(Role.HR.name()));
//        } else if (emp.getEmpGrade().equals("ADMIN")) {
//            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.name()));
//        }

//        // 재직 상태에 따른 추가 권한 부여
//        if (emp.getEmpStatus().equals("재직")) {
//            authorities.add(new SimpleGrantedAuthority(Role.RET.name()));
//        } else if (emp.getEmpStatus().equals("퇴직")) {
//            authorities.add(new SimpleGrantedAuthority(Role.ACT.name()));
//        }





//        //db에서 가지고 온 권한을 GrantedAuthority 로 변환해야함.
//        List<SimpleGrantedAuthority> authList = new ArrayList<SimpleGrantedAuthority>();
//
//        authList.add(new SimpleGrantedAuthority(emp.getEmpGrade().getAuthority()));

        //UsernamePasswordAuthenticationToken(Object principal, Object credentials, authorities)
        //UsernamePasswordAuthenticationToken는 Authentication의 자식객체
        //인증완료된 결과로 UsernamePasswordAuthenticationToken를 리턴한다.

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

//    private Role getRoleFromGrade(String grade) {
//        if (grade.equals("ADMIN")) {
//            return Role.ADMIN;
//        } else if (grade.equals("SW")) {
//            return Role.SW;
//        } else if (grade.equals("DR")) {
//            return Role.DR;
//        } else if (grade.equals("GJ")) {
//            return Role.GJ;
//        } else if (grade.equals("CJ")) {
//            return Role.CJ;
//        } else if (grade.equals("HR")) {
//            return Role.HR;
//        } else {
//            throw new IllegalArgumentException("Invalid employee grade: " + grade);
//        }
//    }


}
