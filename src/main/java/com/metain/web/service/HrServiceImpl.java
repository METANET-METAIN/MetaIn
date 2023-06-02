package com.metain.web.service;

import com.metain.web.domain.Emp;
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


    //전체 신입 사원 목록
    @Override
    public List<NewEmpDTO> newEmpSelectAll() {
        List<NewEmpDTO> list = hrMapper.newEmpSelectAll();
        if(list == null){
            return null;
        }
        return list;
    }
}
