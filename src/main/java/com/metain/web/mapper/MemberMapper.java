package com.metain.web.mapper;

import com.metain.web.domain.Emp;
import com.metain.web.domain.NewEmp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {

    /**로그아웃*/
    public Emp logout(Emp emp);

    public Emp selectAdminInfo(String empDept, String empGrade);



}
