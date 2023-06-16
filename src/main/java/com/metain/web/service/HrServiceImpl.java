package com.metain.web.service;

import com.metain.web.domain.Emp;
import com.metain.web.domain.NewEmp;
import com.metain.web.domain.Role;
import com.metain.web.dto.AlarmDTO;
import com.metain.web.dto.AlarmResponse;
import com.metain.web.dto.NewEmpDTO;
import com.metain.web.mapper.AlarmMapper;
import com.metain.web.mapper.HrMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service
@RequiredArgsConstructor
public class HrServiceImpl implements HrService {
    @Autowired
    private HrMapper hrMapper;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AlarmMapper alarmMapper;
    @Autowired
    private AlarmService alarmService;

    private void roleBasedOnGradeAndStatus(Emp emp) {
        String empGrade = emp.getEmpGrade();
        Role gradeRole = Role.fromGrade(empGrade);
        emp.setRoleName(String.valueOf(gradeRole));

        String empStatus = emp.getEmpStatus();
        Role statusRole = Role.fromStatus(empStatus);
        emp.setRoleName(String.valueOf(statusRole));
    }



    private static final Logger logger = LoggerFactory.getLogger(HrServiceImpl.class);

    // 생년월일을 사용하여 비밀번호 생성
    private String generatePasswordFromBirth(Date birth) {
        // 생년월일을 사용하여 비밀번호 생성 로직 구현
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String birthPassword = dateFormat.format(birth);
        System.out.println("생년월일 -> 비밀번호 (암호화 전) : " + birthPassword);
        return birthPassword;
    }

    // 신입사원 승인
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public int confirmNewEmp(List<NewEmp> newEmpList, Emp emp) {

        int successCount = 0; // 승인된 신입사원 수

        if (newEmpList != null && !newEmpList.isEmpty()) {
            for (NewEmp newEmp : newEmpList) {
                if (newEmp.getNewBirth() != null && !newEmp.getNewBirth().isEmpty()) {
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date empBirthInsert = dateFormat.parse(newEmp.getNewBirth());

                        System.out.println("생년월일 저장!! " + newEmpList);
                        System.out.println("emp!!! " + emp);

                        // 생년월일을 empBirth에 할당
                        emp.setEmpBirth((java.sql.Date) empBirthInsert);

                        // 생년월일 암호화
                        String encodedBirth = generatePasswordFromBirth(empBirthInsert);

                        System.out.println("암호화된 생년월일 저장 전 " + emp);

                        // 비밀번호 암호화하여 empPwd에 할당
                        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                        String encryptedPwd = passwordEncoder.encode(encodedBirth);
                        emp.setEmpPwd(encryptedPwd);

                        emp.setEmpName(newEmp.getNewName());
                        emp.setEmpPhone(newEmp.getNewPhone());
                        emp.setEmpAddr(newEmp.getNewAddr());
                        emp.setEmpZipcode(newEmp.getNewZipcode());
                        emp.setEmpDetailAddr(newEmp.getNewDetailAddr());
                        emp.setEmpEmail(newEmp.getNewEmail());
                        emp.setEmpDept(newEmp.getNewDept());
                        System.out.println("암호화 완료 " + encryptedPwd);

                        roleBasedOnGradeAndStatus(emp); //역할 할당

                        int cnt = hrMapper.confirmEmp(emp);
                        if (cnt >= 1) {
                            successCount++;
                        }


                        //역할 부여
                        int findEmpNo = hrMapper.findEmpNo(emp.getEmpSabun());
                        int findRoleNo = hrMapper.findRoleNo(emp.getRoleName());
                        hrMapper.userRoleSave(findEmpNo, findRoleNo);


                    }   catch (ParseException e) {
                        e.printStackTrace();

                        break;

                    }
                }

                System.out.println(newEmpList);
                System.out.println(emp);

            }

            if (successCount == newEmpList.size()) {
                return hrMapper.deleteNewEmp(newEmpList);
            }
        }
        return 0;
    }


    @Override
    public int updateEmp(Emp emp) {
        return 0;
    }


    @Override
    public List<Emp> selectAll() {
        List<Emp> list = hrMapper.selectAll();
        if(list == null) {
            return null;
        }
        return list;
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
    @Override
    public List<Emp> newEmp() {
        List<Emp> list=hrMapper.newEmp();
        System.out.println(list);
        return list;
    }

    @Override
    public void updateEmp(String empStatus, String empGrade, Long updateEmpId) {
        //업데이트 할 emp 정보 저장
        Emp emp =hrMapper.selectEmpInfo(updateEmpId);
        emp.setEmpStatus(empStatus);
        emp.setEmpGrade(empGrade);
        emp.setEmpId(updateEmpId);
        logger.info("hrService의 updateEmp의 Emp", emp);
        
        //알람 발생
        AlarmDTO alarmDTO=new AlarmDTO();
        alarmDTO.setNotiContent(emp.getEmpName()+ "님의 인사정보가 변경되었습니다.");
        alarmDTO.setNotiUrl("/mypage/update-mypage");
        alarmDTO.setNotiType("인사 정보");
        alarmDTO.setEmpId(updateEmpId);
        alarmMapper.insertAlarm(alarmDTO);

        alarmService.send(updateEmpId, AlarmResponse.comment(emp.getEmpName()+ "님의 인사정보가 변경되었습니다."));
        hrMapper.updateEmp(emp);
    }
}
