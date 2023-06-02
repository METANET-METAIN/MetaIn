package com.metain.web.service;

import com.metain.web.domain.Emp;
import com.metain.web.domain.NewEmp;
import com.metain.web.dto.NewEmpDTO;
import com.metain.web.mapper.HrMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class HrServiceImpl implements HrService {
    @Autowired
    private HrMapper hrMapper;
    @Override
    public List<Emp> empList() {
        return hrMapper.empSelectAll();
    }




    @Override
    public int updateEmp(Emp emp) {
        return 0;
    }


    //신입 사원 등록
    @Override
    public int insertNewEmp(NewEmp newEmp) {
        return hrMapper.insertNewEmp(newEmp);
    }

    //전체 신입 사원 목록
    @Override
    public List<NewEmpDTO> newEmpSelectAll() {
        List<NewEmpDTO> list = hrMapper.newEmpSelectAll();
        if(list == null){
            return null;
        }
        return list;
    }

    @Override
    public Emp selectEmpInfo(Long empId) {
        Emp dbEmp=hrMapper.selectEmpInfo(empId);
        if (dbEmp==null){ //db에서 꺼내온  emp가 null이면 에러페이지 이동
            return null;
        }
        return dbEmp;
    }
    //신입사원 승인
    @Override
    public int confirmNewEmp(List<NewEmp> newEmp) {
//        return hrMapper.deleteNewEmp(newEmp);
        int cnt = hrMapper.confirmEmp(newEmp);
        if(cnt == 1){
            return hrMapper.deleteNewEmp(newEmp);
        }
        return 0;
    }
}
