package com.metain.web.mapper;

import com.metain.web.domain.Emp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {

    /**로그인*/
//    public Emp login(Emp emp);

    /**로그아웃*/
    public Emp logout(Emp emp);

    //로그인
//    public ArrayList<Emp> login(@Param("empSabun") String empSabun);


 //로그인
    public Emp login(@Param("empSabun") String empSabun, @Param("empPwd") String empPwd);

//    사원번호 알아내기
    public int findEmpNo(@Param("empsabun") String empsabun);

//    권한 알아내기
    public int findRole(@Param("empGrade") String empGrade);




}
