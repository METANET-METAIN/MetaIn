package com.metain.web.mapper;

import com.metain.web.domain.Emp;
import com.metain.web.domain.NewEmployee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HrMapper {


    /**인사기록카드 발송(메일 발송)*/
    public void sendMail(NewEmployee newEmployee);

    /**신규 입사자 인사기록카드 작성 */
//   신규입사자 인사기록카드 작성 기능 유무

    /**신규 입사자 승인(수정)
     * 이거 맞는지..*/
    public int updateNewEmp(NewEmployee newEmployee);

    /**신규 입사자 등록*/
    public int insertNewEmp(NewEmployee newEmployee);

    /**신규입사자 전체보기*/
    public List<NewEmployee> newEmpSelectAll();

    /**사원 등록*/
    public int insertEmp(Emp emp);

    /**사원 전체보기*/
    public List<Emp> empSelectAll();

    /**재직자 정보 수정*/
    public int updateEmp(Emp emp);


}
