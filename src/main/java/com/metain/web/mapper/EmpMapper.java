package com.metain.web.mapper;

import com.metain.web.domain.Emp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmpMapper {
    /**로그인*/
    Emp login(Emp emp);

    /**로그아웃*/
    Emp logout(Emp emp);


    /**마이페이지 수정*/
    int updateMyPage(Emp emp);

    /**인사시스템
     * 신규 사원 등록*/


    /**인사시스템
     * 사원 전체보기*/
    List<Emp> selectAll();
}
