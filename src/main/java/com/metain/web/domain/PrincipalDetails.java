package com.metain.web.domain;

import com.metain.web.mapper.HrMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//로그인에 성공하면 Security Session을 생성해 줌 (Key값 : Security ContextHolder)
//Security Session(Authentication(UserDetails)) 이런 식의 구조로 되어있는데
// PrincipalDetails에서 UserDetails를 설정해준다고 보면 됨

/***
 * 사용자의 정보를 담는 클래스
 */

@Component
public class PrincipalDetails implements UserDetails {


    @Autowired
    private Emp emp;

    private Collection<? extends GrantedAuthority> authorities;


    public PrincipalDetails(Emp emp,Collection<? extends GrantedAuthority> authorities) {
        this.emp=emp;
        this.authorities = authorities;
    }




//    private ArrayList<Emp> emp;
//
//    // 생성자 만들기
//    public PrincipalDetails(ArrayList<Emp> empAuthes) {
//        if (empAuthes == null || empAuthes.size() == 0) {
//            throw new IllegalArgumentException("Emp: null or empty");
//        }
//        this.emp = empAuthes;
//    }


    /***
     * getAuthorities()
     * 사용자가 가진 권한을 설정하는 역할
     * 한 계정에 권한을 몇개 가졌는지 확인 가능
     */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        String roles = emp.getRoleName().substring(1, emp.getRoleName().length()-1);
        String[] roleArray = roles.split(", ");

        for (String role : roleArray) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return authorities;
    }



//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//
//        for(int x=0; x<emp.size(); x++) {
//            authorities.add(new SimpleGrantedAuthority(emp.get(x).getRoleName()));
//        }
//
//        return authorities;
//    }


    /***
     * 사원의 비밀번호
     */
    @Override
    public String getPassword() {
        return emp.getEmpPwd();
    }

    /***
     * 사원의 사번
     */
    @Override
    public String getUsername() {
        return emp.getEmpSabun();
    }

    /***
     * 사원의 ID(시퀀스)
     */
    public Long getEmpId() {
        return emp.getEmpId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
