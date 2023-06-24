package com.metain.web.service;

import com.metain.web.domain.Emp;
import com.metain.web.domain.NewEmp;
import com.metain.web.domain.Role;
import com.metain.web.domain.UserRole;
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

    private static final Logger logger = LoggerFactory.getLogger(HrServiceImpl.class);

    // 생년월일을 사용하여 비밀번호 생성
    private String generatePasswordFromBirth(Date birth) {
        // 생년월일을 사용하여 비밀번호 생성 로직 구현
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String birthPassword = dateFormat.format(birth);
        logger.info("HrSer/generatePasswordFromBirth 생년월일 -> 비밀번호 (암호화 전) :",birthPassword);

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
                        emp.setEmpBirth(empBirthInsert);

                        // 생년월일 암호화
                        String encodedBirth = generatePasswordFromBirth(empBirthInsert);

                        System.out.println("암호화된 생년월일 저장 전 " + emp);

                        // 비밀번호 암호화하여 empPwd에 할당
                        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                        String encryptedPwd = passwordEncoder.encode(encodedBirth);
                        emp.setEmpPwd(encryptedPwd);
                        emp.setEmpId(newEmp.getNewId());
                        emp.setEmpName(newEmp.getNewName());
                        emp.setEmpPhone(newEmp.getNewPhone());
                        emp.setEmpAddr(newEmp.getNewAddr());
                        emp.setEmpZipcode(newEmp.getNewZipcode());
                        emp.setEmpDetailAddr(newEmp.getNewDetailAddr());
                        emp.setEmpDept(newEmp.getNewDept());
                        String empGrade = newEmp.getNewGrade();
                        Role gradeRole = Role.fromGrade(empGrade);
                        emp.setEmpGrade(String.valueOf(gradeRole));
//                        emp.setEmpGrade(newEmp.getNewGrade());
//                        emp.setEmpStatus(newEmp.getNewStatus());
                        System.out.println("역할 제대로 들어갔니" + emp);
                        System.out.println("역할 제대로 들어갔니" + newEmp);
                        System.out.println("암호화 완료 " + encryptedPwd);

//                        roleBasedOnGradeAndStatus(emp); //역할 할당

                        int cnt = hrMapper.confirmEmp(emp);
                        if (cnt >= 1) {
                            successCount = 0;
                            successCount++;
                        }


                        //역할 부여
                        Long findRoleNo = hrMapper.findRoleNo(String.valueOf(gradeRole));
                        hrMapper.userRoleSave(emp.getEmpId(), findRoleNo);
                        if (newEmp.getNewStatus().equals("ACTIVE")) {
                            findRoleNo = 7L;
                        } else {
                            findRoleNo = 8L;
                        }

                        hrMapper.userRoleSave(emp.getEmpId(), findRoleNo);


                    } catch (ParseException e) {
                        e.printStackTrace();

                        break;

                    }
                }

                System.out.println(newEmpList);
                System.out.println(emp);

            }

            if (successCount > 0) {
                return hrMapper.deleteNewEmp(newEmpList);
            }
        }
        return 0;
    }


    @Override
    public List<Emp> selectAll() {
        List<Emp> list = hrMapper.selectAll();
        if (list == null) {
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
        if (list == null) {
            return null;
        }
        return list;
    }

    @Override
    public Emp selectEmpInfo(Long empId) {
        Emp dbEmp = hrMapper.selectEmpInfo(empId);
        if (dbEmp == null) { //db에서 꺼내온  emp가 null이면 에러페이지 이동
            return null;
        }
        return dbEmp;
    }

    @Override
    public List<Emp> newEmp() {
        List<Emp> list = hrMapper.newEmp();
        System.out.println(list);
        return list;
    }


    @Override
    public void updateEmp(String empStatus, String empGrade, String empDept, Long updateEmpId, Date empLastDt) {
        //업데이트 할 emp 정보 저장
        Emp emp = hrMapper.selectEmpInfo(updateEmpId);
        emp.setEmpStatus(empStatus);
        emp.setEmpGrade(empGrade);
        emp.setEmpDept(empDept);
        emp.setEmpLastDt(empLastDt);
        emp.setEmpId(updateEmpId);
        logger.info("hrService의 updateEmp의 Emp", emp);

        if(empStatus.equals("RETIREE")) {
            emp.setEmpLastDt(empLastDt);
        } else {
            emp.setEmpLastDt(null);
        }

        UserRole userRole = new UserRole(0L, emp.getEmpId(), 0L);

        List<UserRole> userRoles = hrMapper.selectUserRole(userRole);

        if (userRoles.size() >= 2) {
            Long statusNum = userRoles.get(0).getUrId();
            Long gradeNum = userRoles.get(1).getUrId();
            Long roleId;
            if (empStatus.equals("ACTIVE")) {
                roleId = 7L;
            } else {
                roleId = 8L;
            }
//            if (empStatus.equals("ACTIVE")) {
//                roleId = 7L;
//            } else {
//                roleId = 8L;
//            }


            UserRole userRole1 = new UserRole(statusNum, updateEmpId, roleId);
            hrMapper.updateUserRole(userRole1);
            if (empGrade.equals("EMPLOYEE")) {
                roleId = 1L;
            } else if (empGrade.equals("ASSISTANT")) {
                roleId = 2L;
            } else if (empGrade.equals("MANAGER")) {
                roleId = 3L;
            } else if (empGrade.equals("DEPUTY")) {
                roleId = 4L;
            } else if (empGrade.equals("HR")) {
                roleId = 5L;
            } else {
                roleId = 6L;
            }
            UserRole userRole2 = new UserRole(gradeNum, updateEmpId, roleId);
            hrMapper.updateUserRole(userRole2);
        }

        //알람 발생
        AlarmDTO alarmDTO = new AlarmDTO();
        alarmDTO.setNotiContent(emp.getEmpName() + "님의 인사정보가 변경되었습니다.");
        alarmDTO.setNotiUrl("/mypage/update-mypage");
        alarmDTO.setNotiType("인사 정보");
        alarmDTO.setEmpId(updateEmpId);
        alarmMapper.insertAlarm(alarmDTO);

        alarmService.send(updateEmpId, AlarmResponse.comment(emp.getEmpName() + "님의 인사정보가 변경되었습니다."));
        hrMapper.updateEmp(emp);
    }

}
