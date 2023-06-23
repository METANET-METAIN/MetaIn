package com.metain.web.security;

import com.metain.web.domain.Emp;
import com.metain.web.domain.PrincipalDetails;
import com.metain.web.mapper.HrMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PrincipalService implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HrMapper hrMapper;

    /***
     * 사용자의 상세 정보를 조회하고
     * Custom한 Userdetails 클래스를 리턴
     *
     */
    @Override
    public UserDetails loadUserByUsername(String empSabun) throws UsernameNotFoundException {

        logger.info("-----[PrincipalService] empSabun ::::: {}", empSabun);

        Emp emp = hrMapper.login(empSabun);

        logger.info("-----[PrincipalService] emp ::::: {}", emp);



        List<String> empRoles = hrMapper.selectRoleName(empSabun);

        logger.info("-----[PrincipalService] empRoles ::::: {}", empRoles);


        if (empRoles != null) {
            logger.info("PrincipalService/loadUserByUsername if문 진입 확인로그");
            List<GrantedAuthority> authorities = new ArrayList<>();
//            String roles[] = emp.getRoleName().split(",");

            for (int i = 0; i < empRoles.size(); i++) {
                logger.info("PrincipalService/loadUserByUsername for문 진입 확인로그");
                authorities.add(new SimpleGrantedAuthority(empRoles.get(i)));
                logger.info("PrincipalService/loadUserByUsername empRoles",empRoles.get(i));
            }
            //SecurityContext의 Authertication에 등록되어 인증정보를 가짐
            emp.setAuthorities(authorities);
            emp.setRoleName(authorities.toString());
            PrincipalDetails principalDetails = new PrincipalDetails(emp,authorities);
            return principalDetails;


        }

        throw new UsernameNotFoundException("사용자 권한 정보를 찾을 수 없습니다.");
    }


}
