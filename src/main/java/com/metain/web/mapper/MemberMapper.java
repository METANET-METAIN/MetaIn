package com.metain.web.mapper;

import com.metain.web.domain.Emp;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    /**로그인*/
    public Emp login(Emp emp);

    /**로그아웃*/
    public Emp logout(Emp emp);


}
