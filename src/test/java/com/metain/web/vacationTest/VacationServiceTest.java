package com.metain.web.vacationTest;


import com.metain.web.controller.VacationController;
import com.metain.web.domain.Emp;
import com.metain.web.domain.PrincipalDetails;
import com.metain.web.domain.Vacation;
import com.metain.web.dto.AlarmDTO;
import com.metain.web.dto.VacationListDTO;
import com.metain.web.mapper.AlarmMapper;
import com.metain.web.mapper.HrMapper;
import com.metain.web.mapper.VacationMapper;
import com.metain.web.service.HrService;
import com.metain.web.service.MemberService;
import com.metain.web.service.VacationService;
import com.metain.web.service.VacationServiceImpl;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.*;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class VacationServiceTest {
    @Mock
    private HrMapper hrMapper;

    @Mock
    private VacationMapper vacationMapper;
    @Mock
    private AlarmMapper alarmMapper;
    @InjectMocks //injection은 인터페이스 넣을 수 없다
    private VacationServiceImpl vacationService;
    @Autowired
    private MockMvc mockMvc; // MockMvc 선언


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vacationService).build();
    }
    @Test
    public void selectAllListService(){
        //given
        List<VacationListDTO> list=new ArrayList<>();
        // When
        when(vacationMapper.selectAllList()).thenReturn(list);
        List<VacationListDTO> result= vacationMapper.selectAllList();
        // Then
        assertEquals(list,result);
        verify(vacationMapper, times(1)).selectAllList();
    }
    @Test
    public void insertVacationService() {
        // Given
        Emp fakeEmp = new Emp();
        fakeEmp.setEmpId(152L);
        int diffDays = 2;
        Vacation fakeVacation = new Vacation();
        fakeVacation.setVacType("병가");

        //모킹~~
        when(hrMapper.selectEmpInfo(fakeEmp.getEmpId())).thenReturn(fakeEmp);
        System.out.println(fakeEmp);

        when(vacationMapper.requestVacation(fakeVacation)).thenReturn(1);
        when(vacationMapper.decreaseVacation(diffDays, fakeEmp.getEmpId())).thenReturn(1);

        // When
        vacationMapper.requestVacation(fakeVacation);
        // Then
        verify(vacationMapper, times(1)).requestVacation(fakeVacation);
        verify(vacationMapper, times(1)).decreaseVacation(diffDays,fakeEmp.getEmpId());

    }
}
