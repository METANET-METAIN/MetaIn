package com.metain.web.service;

import com.metain.web.domain.Emp;
import com.metain.web.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    public MemberMapper memberMapper;


    @Override
    public Emp login(Emp emp) {
        return memberMapper.login(emp.getEmpSabun(), emp.getEmpPwd());
    }
}
