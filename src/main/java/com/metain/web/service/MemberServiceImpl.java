package com.metain.web.service;

import com.metain.web.domain.Emp;
import com.metain.web.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    public MemberMapper memberMapper;


    @Override
    public Emp login(Emp emp) {
        return memberMapper.login(String.valueOf(emp));
    }
}
