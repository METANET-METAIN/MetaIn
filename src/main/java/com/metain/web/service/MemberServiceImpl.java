package com.metain.web.service;

import com.metain.web.domain.Emp;
import com.metain.web.domain.NewEmp;
import com.metain.web.dto.PrincipalDetails;
import com.metain.web.mapper.HrMapper;
import com.metain.web.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    public MemberMapper memberMapper;

    @Autowired
    public HrMapper hrMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;




    @Override
    public Emp selectAdminInfo(String empDept, String empGrade) {
        Emp adminInfo=memberMapper.selectAdminInfo(empDept, empGrade) ;
            if(adminInfo==null && empGrade!="팀관리자"){
                return null;
            }
        return adminInfo;
    }


}
