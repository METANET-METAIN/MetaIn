package com.metain.web.service;

import com.metain.web.domain.Emp;
import com.metain.web.domain.EmpCert;
import com.metain.web.domain.ExperienceCert;
import com.metain.web.domain.RetireCert;
import com.metain.web.dto.AlarmDTO;
import com.metain.web.dto.MyVacDTO;
import com.metain.web.mapper.HrMapper;
import com.metain.web.mapper.MyPageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class MyPageServiceImpl implements MyPageService{

    @Autowired
    private MyPageMapper myPageMapper;

    @Autowired
    private HrMapper hrMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


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
    public void updateMy(Emp emp, MultipartFile file) throws IOException {
        Emp dbemp = hrMapper.selectEmpInfo(emp.getEmpId());
        String encryptedPwd = bCryptPasswordEncoder.encode(emp.getEmpPwd());
        System.out.println(encryptedPwd);
        System.out.println("updateMy : " + dbemp);

        dbemp.setEmpPwd(encryptedPwd);
        dbemp.setEmpAddr(emp.getEmpAddr());
        dbemp.setEmpPhone(emp.getEmpPhone());
        dbemp.setEmpZipcode(emp.getEmpZipcode());
        dbemp.setEmpDetailAddr(emp.getEmpDetailAddr());
        String sabun = emp.getEmpSabun();
        UUID uuid = UUID.randomUUID();

        //여기서 오류남
        String originalImgName = file.getOriginalFilename();
        String extension = originalImgName.substring(originalImgName.lastIndexOf("."));

        String savedImgName = sabun + uuid.toString().substring(0, 5) + extension;
        String savePath = System.getProperty("user.dir") +
                            "/src/main/resources/static/file/" + savedImgName;
        System.out.println(savePath);
        File destImg = new File(savePath);
        file.transferTo(destImg);

        dbemp.setEmpProfile(savedImgName);

        myPageMapper.updateMyPage(dbemp);
    }
}
