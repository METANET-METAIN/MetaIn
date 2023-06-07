//package com.metain.web.service;
//
//import com.metain.web.domain.Emp;
//import com.metain.web.dto.UserPrincipalVO;
//import com.metain.web.mapper.MemberMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.nio.file.attribute.UserPrincipal;
//import java.util.ArrayList;
//
//@Service
//@RequiredArgsConstructor
//public class HomeService implements UserDetailsService {
//
//    @Autowired
//    private MemberMapper memberMapper;
//
//    @Override
//    public UserDetails loadUserByUsername(String empSabun) throws UsernameNotFoundException {
//        Emp emp = memberMapper.login(empSabun)
//                .orElseThrow(() -> new UsernameNotFoundException("Emp " + empSabun + " Not Found!"));
//        return new UserPrincipalVO(emp);
//    }
//}
