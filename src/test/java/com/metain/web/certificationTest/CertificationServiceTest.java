package com.metain.web.certificationTest;

import com.metain.web.domain.EmpCert;
import com.metain.web.dto.CertInfoDTO;
import com.metain.web.mapper.CertificationMapper;

import com.metain.web.service.CertificationService;
import com.metain.web.service.CertificationServiceImpl;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CertificationServiceTest {
    @Mock
    private CertificationMapper certificationMapper;
    @InjectMocks
    private CertificationServiceImpl certificationService;
    @Autowired
    private MockMvc mockMvc; // MockMvc 선언

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(certificationService).build();
    }

    @Test
    public void applyAndSelectEmpCert() {
        // Given
        String expectedViewName = "/certification/cert-complete";
        CertInfoDTO fakeCertInfoDTO = new CertInfoDTO();
        fakeCertInfoDTO.setEmpCertId(237L);
        fakeCertInfoDTO.setCertSort("A01");

        // 이전 쿼리 실행 후 자동으로 생성된 ID 값을 가져옴
        int fakeLastId = certificationMapper.getLastEmpCertId();

        certificationMapper.insertEmpCert(fakeCertInfoDTO);
        EmpCert fakeEmpCert = certificationMapper.selectEmpCert(fakeCertInfoDTO.getEmpCertId());

    }
    @Test
    public void applyAndSelectExperCert() {
        // Given
        String expectedViewName = "/certification/cert-complete";
        CertInfoDTO fakeCertInfoDTO = new CertInfoDTO();
        fakeCertInfoDTO.setEmpCertId(35L);
        fakeCertInfoDTO.setCertSort("A02");

        // 이전 쿼리 실행 후 자동으로 생성된 ID 값을 가져옴
        int fakeLastId = certificationMapper.getLastEmpCertId();

        certificationMapper.insertEmpCert(fakeCertInfoDTO);
        EmpCert fakeEmpCert = certificationMapper.selectEmpCert(fakeCertInfoDTO.getEmpCertId());

    }
    @Test
    public void applyAndSelectRetireCert() {
        // Given
        String expectedViewName = "/certification/cert-complete";
        CertInfoDTO fakeCertInfoDTO = new CertInfoDTO();
        fakeCertInfoDTO.setEmpCertId(25L);
        fakeCertInfoDTO.setCertSort("A03");

        // 이전 쿼리 실행 후 자동으로 생성된 ID 값을 가져옴
        int fakeLastId = certificationMapper.getLastEmpCertId();

        certificationMapper.insertEmpCert(fakeCertInfoDTO);
        EmpCert fakeEmpCert = certificationMapper.selectEmpCert(fakeCertInfoDTO.getEmpCertId());

    }

}
