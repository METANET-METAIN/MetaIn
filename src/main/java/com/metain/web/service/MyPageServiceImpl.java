package com.metain.web.service;

import com.metain.web.dto.MyCertListDTO;
import com.metain.web.dto.MyVacDTO;
import com.metain.web.mapper.MyPageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyPageServiceImpl implements MyPageService{

    @Autowired
    private MyPageMapper myPageMapper;
    @Override
    public List<MyCertListDTO> selectIssueAll() {
        List<MyCertListDTO> list = myPageMapper.selectIssueAll();
        if(list == null){
            return null;
        }
        return list;

    }

    @Override
    public List<MyVacDTO> selectMyVacList(MyVacDTO myVacDTO) {
        List<MyVacDTO> list = myPageMapper.selectMyVacList(myVacDTO);
        if(list == null){
            return null;
        }
        return list;
    }

    @Override
    public List<MyVacDTO> myVacList(Long empId) {
        List<MyVacDTO> list =myPageMapper.myVacList(empId);
        return list;
    }
}
