package com.metain.web.service;

import com.metain.web.domain.Emp;
import com.metain.web.domain.EmpCert;
import com.metain.web.domain.ExperienceCert;
import com.metain.web.domain.RetireCert;
import com.metain.web.dto.AlarmDTO;
import com.metain.web.dto.MyCertDTO;
import com.metain.web.dto.MyVacDTO;
import com.metain.web.mapper.MyPageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyPageServiceImpl implements MyPageService{

    @Autowired
    private MyPageMapper myPageMapper;
//    @Override
//    public List<MyCertDTO> selectIssueAll() {
//        List<MyCertDTO> list = myPageMapper.selectIssueAll();
//        if(list == null){
//            return null;
//        }
//        return list;
//
//    }

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

    //재직증명서 목록
    @Override
    public List<EmpCert> selectMyEmpCert(EmpCert empCert) {
        List<EmpCert> list = myPageMapper.selectMyEmpCert();
        return list;
    }


    //경력증명서 목록
    @Override
    public List<ExperienceCert> selectMyExperCert(ExperienceCert experienceCert) {
        List<ExperienceCert> list = myPageMapper.selectMyExperCert();
        return list;
    }

    //퇴직증명서 목록
    @Override
    public List<RetireCert> selectMyRetCert(RetireCert retireCert) {
        List<RetireCert> list = myPageMapper.selectMyRetCert();
        return list;
    }

    @Override
    public List<AlarmDTO> alarmList(Long empId) {
        List<AlarmDTO> list= myPageMapper.alarmList(empId);
        if(list==null){
            return null;
        }else return list;

    }

    @Override
    public void updateMy(Emp dbemp) {
        myPageMapper.updateMyPage(dbemp);
    }
}
