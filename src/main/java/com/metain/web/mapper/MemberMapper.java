package com.metain.web.mapper;

import com.metain.web.domain.Emp;
import com.metain.web.domain.NewEmp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {

    /**로그인*/
//    public Emp login(Emp emp);

    /**로그아웃*/
    public Emp logout(Emp emp);

    //로그인
//    public ArrayList<Emp> login(@Param("empSabun") String empSabun);

//    /**신규 입사자 정식 등록*/
//    public int confirmEmp(List<NewEmp> newEmp);


//    //로그인
//    public Emp login(String empSabun);
//
////    사원번호 알아내기
//    public int findEmpNo(String empsabun);
//
////    권한 알아내기
//    public int findRole(String empGrade);

    public Emp selectAdminInfo(String empDept, String empGrade);



}
