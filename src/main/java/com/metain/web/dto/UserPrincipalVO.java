//package com.metain.web.dto;
//
//import com.metain.web.domain.Emp;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//public class UserPrincipalVO implements UserDetails {
//
//    private User user;
//
//    public UserPrincipalVO(User user) {
//        this.user = user;
//    }
//
////    권한 관련 작업을 하기 위한 role return
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> collections = new ArrayList<>();
//        collections.add(() -> {
//            return user.getRole().name();
//        });
//        return collections;
//    }
//
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }
//
//    // get Username 메서드 (생성한 User은 loginId 사용)
//    @Override
//    public String getUsername() {
//        return user.getUsername();
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
//}
