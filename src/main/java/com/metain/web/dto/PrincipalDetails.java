package com.metain.web.dto;

import com.metain.web.domain.Emp;
import com.metain.web.mapper.HrMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
//로그인에 성공하면 Security Session을 생성해 줌 (Key값 : Security ContextHolder)
//Security Session(Authentication(UserDetails)) 이런 식의 구조로 되어있는데
// PrincipalDetails에서 UserDetails를 설정해준다고 보면 됨
public class PrincipalDetails implements AuthenticationProvider {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private HrMapper hrMapper;
//
//    public PrincipalDetails(Emp emp) {
//        this.emp = emp;
//    }
//
////    @Override
////    public Collection<? extends GrantedAuthority> getAuthorities() { //유저가 갖고 있는 권한 목록
////
////        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
////
////        for(int x=0; x<emp.size(); x++) {
////            authorities.add(new SimpleGrantedAuthority(emp.get(x).getEmpGrade().toString()));
////        }
////
////        return authorities;
////    }
//
//
//
//
////    @Autowired
////    private Emp emp;
////
////
////    public PrincipalDetails(Emp emp){
////
////        if (emp == null) {
////            throw new IllegalArgumentException("Emp : null");
////        }
////        this.emp = emp;
////    }
//
//    //    권한 관련 작업을 하기 위한 role return
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        if (emp != null && emp.getEmpGrade() != null) {
//            authorities.add(() -> String.valueOf(new SimpleGrantedAuthority(emp.getEmpGrade().value())));
//        }
//
//        return (Collection<? extends GrantedAuthority>) new UsernamePasswordAuthenticationToken(emp, null, authorities);
//    }
//
//
//    @Override
//    public String getPassword() {
//        return emp.getEmpPwd();
//    }
//
//    // get Username 메서드 (생성한 User은 loginId 사용)
//    @Override
//    public String getUsername() {
//        return emp.getEmpSabun();
//    }
//
//    public Long getEmpId() {
//        return emp.getEmpId();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String empSabun = (authentication.getName());
        Emp emp = hrMapper.login(empSabun);
        System.out.println("pd authebapthem"+emp);
        if(emp==null){// ID가 없는경우

            throw new RuntimeException("아이디 혹은 비밀번호를 다시 확인해주세요.");//spring exception
        }

        String password = (String)authentication.getCredentials();//비밀번호
        System.out.println("password = " + password);
        if(!password.equals(emp.getEmpPwd())) {
            if(!bCryptPasswordEncoder.matches(password, emp.getEmpPwd())){
                throw new RuntimeException("아이디 혹은 비밀번호를 다시 확인해주세요.");
            }
        }
        //db에서 가지고 온 권한을 GrantedAuthority 로 변환해야함.
        List<SimpleGrantedAuthority> authList = new ArrayList<SimpleGrantedAuthority>();

        authList.add(new SimpleGrantedAuthority(emp.getEmpGrade().getAuthority()));

        //UsernamePasswordAuthenticationToken(Object principal, Object credentials, authorities)
        //UsernamePasswordAuthenticationToken는 Authentication의 자식객체
        //인증완료된 결과로 UsernamePasswordAuthenticationToken를 리턴한다.
        return new UsernamePasswordAuthenticationToken(emp, null, authList);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);

    }
}
