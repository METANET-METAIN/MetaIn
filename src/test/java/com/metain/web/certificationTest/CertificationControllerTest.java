package com.metain.web.certificationTest;

import com.metain.web.controller.CertificationController;
import com.metain.web.controller.VacationController;
import com.metain.web.domain.*;
import com.metain.web.dto.CertInfoDTO;
import com.metain.web.dto.VacationListDTO;
import com.metain.web.mapper.AlarmMapper;
import com.metain.web.mapper.HrMapper;
import com.metain.web.service.*;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CertificationControllerTest {
    @Mock
    private CertificationService certificationService;
    @Mock
    private MyPageService myPageService;
    @InjectMocks
    private CertificationController certificationController;
    @Autowired
    private MockMvc mockMvc; // MockMvc 선언

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(certificationController).build();
    }
    @Test
    public void handleEmpCertInfo() {
        // Given
        String expectedViewName = "/certification/cert-complete";
        Emp fakeEmp = new Emp();
        fakeEmp.setEmpId(152L);

        CertInfoDTO fakeCertInfoDTO=new CertInfoDTO();
        fakeCertInfoDTO.setEmpCertId(237L);
        fakeCertInfoDTO.setCertSort("A01");
       // 모킹
        EmpCert fakeEmpCert=new EmpCert();
        when( certificationService.applyAndSelectEmpCert(fakeCertInfoDTO)).thenReturn(fakeEmpCert);
        // When
        EmpCert re=certificationService.applyAndSelectEmpCert(fakeCertInfoDTO);
        // Then
        assertEquals(expectedViewName,"/certification/cert-complete");
        assertEquals(fakeEmpCert,re);
    }
    @Test
    public void handleExpCertInfo() {
        // Given
        String expectedViewName = "/certification/cert-complete";
        Emp fakeEmp = new Emp();
        fakeEmp.setEmpId(152L);

        CertInfoDTO fakeCertInfoDTO=new CertInfoDTO();
        fakeCertInfoDTO.setEmpCertId(35L);
        fakeCertInfoDTO.setCertSort("A02");
        // 모킹
        ExperienceCert fakeEmpCert=new ExperienceCert();
        when( certificationService.applyAndSelectExperCert(fakeCertInfoDTO)).thenReturn(fakeEmpCert);
        // When
        ExperienceCert re=certificationService.applyAndSelectExperCert(fakeCertInfoDTO);
        // Then
        assertEquals(expectedViewName,"/certification/cert-complete");
        assertEquals(fakeEmpCert,re);
    }
    @Test
    public void handleRetireCertInfo() {
        // Given
        String expectedViewName = "/certification/cert-complete";
        Emp fakeEmp = new Emp();
        fakeEmp.setEmpId(152L);

        CertInfoDTO fakeCertInfoDTO=new CertInfoDTO();
        fakeCertInfoDTO.setEmpCertId(35L);
        fakeCertInfoDTO.setCertSort("A03");
        // 모킹
        RetireCert fakeEmpCert=new RetireCert();
        when( certificationService.applyAndSelectRetireCert(fakeCertInfoDTO)).thenReturn(fakeEmpCert);
        // When
        RetireCert re=certificationService.applyAndSelectRetireCert(fakeCertInfoDTO);
        // Then
        assertEquals(expectedViewName,"/certification/cert-complete");
        assertEquals(fakeEmpCert,re);
    }
}
