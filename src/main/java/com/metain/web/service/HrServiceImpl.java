package com.metain.web.service;

import com.metain.web.domain.Emp;
import com.metain.web.domain.NewEmp;
import com.metain.web.dto.NewEmpDTO;
import com.metain.web.dto.PrincipalDetails;
import com.metain.web.mapper.HrMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
 public class HrServiceImpl implements HrService, UserDetailsService {
    @Autowired
    private HrMapper hrMapper;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


//
//    @Override
//    public Emp login(Emp emp) {
//        return null;
//    }

    //    db로부터 사원정보를 가져와 사원인지 아닌지를 체크하는 메소드
    @Override
    public UserDetails loadUserByUsername(String empSabun) throws UsernameNotFoundException {
        System.out.println("HrServiceImpl = " + empSabun);

        // DB로부터 회원 정보를 가져온다.
        Emp emp = hrMapper.login(empSabun);
        System.out.println(emp.getEmpSabun());

        if (emp == null) {
            throw new UsernameNotFoundException(empSabun + "의 사원이 존재하지 않습니다.");
        }

        return new PrincipalDetails(emp); // UserDetails 클래스를 상속받은 PrincipalDetails 리턴한다.


    }



    // 신입사원 승인
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public int confirmNewEmp(List<NewEmp> newEmpList, Emp emp) {
        if (newEmpList != null && !newEmpList.isEmpty()) {
            for (NewEmp newEmp : newEmpList) {
                if (newEmp.getNewBirth() != null && !newEmp.getNewBirth().isEmpty()) {
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date empBirth = dateFormat.parse(newEmp.getNewBirth());
                        emp.setEmpBirth(empBirth);
                        System.out.println("생년월일 저장!! " + newEmpList);
                        System.out.println("emp!!! " + emp);

                        // 생년월일 암호화
                        String encodedBirth = bCryptPasswordEncoder.encode(newEmp.getNewBirth());
                        System.out.println("암호화된 생년월일 저장!! " + newEmpList);
                        System.out.println("emp!!! " + emp);

                        emp.setEmpPwd(encodedBirth);
                        System.out.println("암호화된 생년월일 저장!! " + newEmpList);
                        System.out.println("emp!!! " + emp);


                        break;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            System.out.println(newEmpList);
            System.out.println(emp);

            // emp 테이블에 저장하기 전에 empPwd 필드에 암호화된 생년월일 할당

            int cnt = hrMapper.confirmEmp(newEmpList, emp);
            if (cnt >= 1) {
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




}
