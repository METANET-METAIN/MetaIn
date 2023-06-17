package com.metain.web.security;

import com.metain.web.domain.Emp;
import com.metain.web.domain.PrincipalDetails;
import com.metain.web.mapper.HrMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final HrMapper hrMapper;


    public PrincipalService(HrMapper hrMapper) {
        this.hrMapper = hrMapper;
    }

//    public Emp findEmpNo(String empSabun){
//        int empId = hrMapper.findEmpNo(empSabun);
//        Emp emp =
//        return new PrincipalDetails(emp);
//
//    }

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


        System.out.println("empRoles" + empRoles);

        if (empRoles != null) {
            System.out.println("오냐?");
            List<GrantedAuthority> authorities = new ArrayList<>();
//            String roles[] = emp.getRoleName().split(",");

            for (int i = 0; i < empRoles.size(); i++) {
                System.out.println("들어오는지");
                authorities.add(new SimpleGrantedAuthority(empRoles.get(i)));
                System.out.println(empRoles.get(i));
            }
            //SecurityContext의 Authertication에 등록되어 인증정보를 가짐
            emp.setAuthorities(authorities);
            emp.setRoleName(authorities.toString());
            PrincipalDetails principalDetails = new PrincipalDetails(emp,authorities);
            return principalDetails;


        }

        return null;
    }


//    @Override
//    public UserDetails loadUserByUsername(String empSabun) throws UsernameNotFoundException {
//
//        logger.info("-----[PrincipalService] empSabun ::::: {}", empSabun);
//
//
//        //로그인 및 사원 정보 조회 로직
//        ArrayList<Emp> empAuthes = hrMapper.login(empSabun);
//
//        if(empAuthes.size() == 0) {
//            throw new UsernameNotFoundException("Emp" + empSabun + "Not Found");
//        } return new PrincipalDetails(empAuthes);

//        if (emp != null) {
//            List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
//            List<String> roles= new ArrayList<>();
//            for(int i = 0 ; i<emp.size();i++){
//                roles.add(emp.get(i).getRoleName());
//            }
//
//            for (String role : roles) {
//                authorityList.add(new SimpleGrantedAuthority(role));
//            }
//            return new PrincipalDetails(empAuthes);
//        }


}
