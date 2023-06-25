package com.metain.web.mapper;

import com.metain.web.domain.Emp;
import com.metain.web.domain.NewEmp;
import com.metain.web.domain.UserRole;
import com.metain.web.dto.NewEmpDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HrMapper {

    //로그인
    public Emp login(String empSabun);

    //role 확인하기
    List<String> selectRoleName(String empSabun);

    //권한 저장
    public int userRoleSave(Long empId, Long roleId);
    //    권한 알아내기
    public Long findRoleNo(String roleName);
    /**신규 입사자 등록*/
    public int insertNewEmp(NewEmp newEmp);

    /**신규 입사자 승인(삭제)*/
    public int deleteNewEmp(List<NewEmp> newEmp);

    /**신규입사자 전체보기*/
    public List<NewEmpDTO> newEmpSelectAll();

    /**신규 입사자 정식 등록*/
    public int confirmEmp(Emp emp);

    /**사원 전체보기*/
    public List<Emp> selectAll();

    /**인사 정보 상세 조회*/
    public Emp selectEmpInfo(Long empId);

    /**인사 정보 수정(직급, 부서, 재직상태)*/
    public int updateEmp(Emp emp);

    /**배치 스케쥴러 통한 연차 초기화*/
    public int annualUpdate(Emp empInfo);

    List<Emp> newEmp();

    public void updateUserRole(UserRole userRole1);

    public List<UserRole> selectUserRole(UserRole userRole);

}
