package com.metain.web.hrTest;

import com.metain.web.domain.Emp;
import com.metain.web.mapper.HrMapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.module.FindException;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HRMybatisTest {
    @Autowired
    private HrMapper hrMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    private final String EMP_SABUN = "20230209";
    private final String EMP_PWD = "19970921";

    private String encryptedPassword;

    @BeforeAll
    public void setTestData(){
        System.out.println("setTestData 실행");
        //비밀번호 암호화
        encryptedPassword = bCryptPasswordEncoder.encode(EMP_PWD);
        Emp emp = new Emp();
        emp.setEmpSabun(EMP_SABUN);
        emp.setEmpPwd(encryptedPassword);

        hrMapper.login(emp.getEmpSabun());

    }

    @DisplayName("login 메소드 테스트")
    @Test
    public void login_emp(){
        System.out.println("login_emp 실행");

        try {
            //로그인 쿼리 실행
            Emp emp = hrMapper.login(EMP_SABUN);
            //쿼리 결과 확인
            assertEquals(EMP_SABUN, emp.getEmpSabun());
            //비밀번호 검증
            boolean isPwdMatch = bCryptPasswordEncoder.matches(EMP_PWD, emp.getEmpPwd());
            assertEquals(true, isPwdMatch);
//            assertEquals(encryptedPassword, emp.getEmpPwd());
        } catch (FindException e) {
            e.printStackTrace();
            Assertions.fail("Exception : " + e.getMessage());
        }
    }



}
