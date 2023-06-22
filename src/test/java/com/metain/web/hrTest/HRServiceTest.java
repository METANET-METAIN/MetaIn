package com.metain.web.hrTest;

import com.metain.web.domain.Emp;
import com.metain.web.domain.NewEmp;
import com.metain.web.mapper.HrMapper;
import com.metain.web.service.HrService;
import com.metain.web.service.HrServiceImpl;
import com.metain.web.service.VacationService;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.client.ExpectedCount;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HRServiceTest {

    @Autowired
    private HrService hrService;

    @MockBean
    private HrMapper hrMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("insertNewEmp 테스트")
    public void insertNewEmpTest() throws Exception {
        NewEmp newDto = new NewEmp();

        newDto.setNewId(300L);
        newDto.setNewName("suhyun1");
        newDto.setNewPhone("010-1111-1112");
        newDto.setNewAddr("경기 의정부시 가금로 29");
        newDto.setNewZipcode("11611");
        newDto.setNewDetailAddr("301동");
        newDto.setNewDept("개발 1팀");
        newDto.setNewBirth("1997-01-02");
        newDto.setNewGrade("EMPLOYEE");

        // when
        hrService.insertNewEmp(newDto);

        // then
        verify(hrMapper, times(1)).insertNewEmp(newDto);

    }

    @Test
    @DisplayName("selectEmp테스트")
    public void selectEmpTest() {
        // given
        Long empId = 151L;
        Emp empDto = new Emp();
        // Set other properties of expectedEmp
        when(hrMapper.selectEmpInfo(empId)).thenReturn(empDto);

        // when
        Emp empReal = hrService.selectEmpInfo(empId);

        // then
//        verify(hrMapper, times(1)).selectEmpInfo(empId);
        assertEquals(empDto, empReal);
        verify(hrMapper, times(1)).selectEmpInfo(empId);

    }

    @Test
    @DisplayName("updateEmp 테스트")
    public void updateEmpTest() {
        // given

        Emp empDto = hrService.selectEmpInfo(151L);

        empDto.setEmpStatus("ACTIVE");
        empDto.setEmpGrade("EMPLOYEE");
        empDto.setEmpDept("개발 3팀");


        // when
        hrService.updateEmp(empDto.getEmpStatus(), empDto.getEmpGrade(),empDto.getEmpDept(),151L);

        // then
        verify(hrMapper, times(1)).updateEmp(empDto);
    }
}