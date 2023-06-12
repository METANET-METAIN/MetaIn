package com.metain.web.mapper;

import com.metain.web.domain.Emp;
import com.metain.web.domain.NewEmp;
import com.metain.web.domain.Role;
import com.metain.web.dto.NewEmpDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface HrMapper {

    //로그인
    public Emp login(String empSabun);

    //비밀번호 업데이트
    public int updatePwdEmp(Emp emp);

    //    사원번호 알아내기
    public int findEmpNo(String empsabun);

    //    권한 알아내기
    public Role findRole(String empGrade);



    /**신규 입사자 등록*/
    public int insertNewEmp(NewEmp newEmp);

    /**인사기록카드 발송(메일 발송)*/
    public void sendMail(NewEmp newEmp);

    /**신규 입사자 인사기록카드 작성 및 전송(수정) */
    public int updateNewEmp(NewEmp newEmp);

    /**신규 입사자 승인(삭제)
     * 이거 맞는지..*/
    public int deleteNewEmp(List<NewEmp> newEmp);

    /**신규입사자 전체보기*/
    public List<NewEmpDTO> newEmpSelectAll();

    /**신규 입사자 정식 등록*/
    public int confirmEmp(List<NewEmp> newEmp, Emp emp);

    /**사원 전체보기*/
    public List<Emp> selectAll();

    /**부서별 사원 조회*/
    public List<Emp> selectListByDept(String empDept);

    /**재직상태별 사원 조회*/
    public List<Emp> selectListByStatus(String empStatus);


    /**입사일자별 사원 조회*/
    public List<Emp> selectListByFistDt(String empFistDt);

    /**인사 정보 상세 조회*/
    public Emp selectEmpInfo(Long empId);

    /**인사 정보 수정(직급, 부서, 재직상태)*/
    public int updateEmp(Emp emp);




}
