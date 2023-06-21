package com.metain.web.vacationTest;

import com.metain.web.controller.VacationController;
import com.metain.web.domain.Emp;
import com.metain.web.domain.PrincipalDetails;
import com.metain.web.domain.Vacation;
import com.metain.web.dto.AlarmDTO;
import com.metain.web.dto.VacationListDTO;
import com.metain.web.mapper.AlarmMapper;
import com.metain.web.mapper.HrMapper;
import com.metain.web.service.HrService;
import com.metain.web.service.MemberService;
import com.metain.web.service.VacationService;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.*;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VacationControllerTest {
    @Mock
    private HrMapper hrMapper;
    @Mock
    private HrService hrService;
    @Mock
    private VacationService vacationService;
    @Mock
    private AlarmMapper alarmMapper;
    @InjectMocks
    private VacationController vacationController;
    @Autowired
    private MockMvc mockMvc; // MockMvc 선언
    @Mock
    private MemberService memberService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vacationController).build(); // HomeController를 MockMvc에 설정

    }

    @Test
    public void vacationList() {
        // Given
        String expectedViewName = "/vacation/vacation-list";
        Emp fakeEmp = new Emp();
        fakeEmp.setEmpId(152L);

        // hrMapper의 필요한 동작을 모킹합니다.
        when(hrService.selectEmpInfo(fakeEmp.getEmpId())).thenReturn(fakeEmp);
        System.out.println(fakeEmp);
        List<String> auth = Arrays.asList("EMPLOYEE","ACTIVE");
        when(hrMapper.selectRoleName(fakeEmp.getEmpSabun())).thenReturn(auth);

        List<VacationListDTO> list=new ArrayList<>();
        when(vacationService.selectAllList()).thenReturn(list);

        List<GrantedAuthority> authorities = auth.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        fakeEmp.setAuthorities(authorities);
        fakeEmp.setRoleName(authorities.toString());
        fakeEmp.setRoleId(1L);
        fakeEmp.setRoleId(7L); //
        PrincipalDetails principalDetails = new PrincipalDetails(fakeEmp, authorities);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, authorities);

        Model model = mock(Model.class); // Mock으로 Model 객체 생성
        model.addAttribute("emp", fakeEmp);
        // When
        List<VacationListDTO> result= vacationService.selectAllList();
        System.out.println(result);
        // Then
        assertEquals(expectedViewName, "/vacation/vacation-list");
        assertEquals(list,result);
        verify(vacationService, times(1)).selectAllList();
        //verify(hrMapper, times(1)).selectRoleName(fakeEmp.getEmpSabun());
    }

    @Test
    public void insertVacation() {
        // Given
        String expectedViewName = "redirect:/mypage/my-vac-list";
        Emp fakeEmp = new Emp();
        fakeEmp.setEmpId(152L);
        int diffDays = 2;
        Vacation fakeVacation = new Vacation();
        fakeVacation.setVacType("병가");

        // hrMapper의 필요한 동작을 모킹합니다.
        when(hrService.selectEmpInfo(fakeEmp.getEmpId())).thenReturn(fakeEmp);
        System.out.println(fakeEmp);
        List<String> auth = Arrays.asList("EMPLOYEE", "ACTIVE");
        when(hrMapper.selectRoleName(fakeEmp.getEmpSabun())).thenReturn(auth);

        doNothing().when(vacationService).insertVacation(fakeVacation,diffDays, fakeEmp.getEmpId());

        List<GrantedAuthority> authorities = auth.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        fakeEmp.setAuthorities(authorities);
        fakeEmp.setRoleName(authorities.toString());
        fakeEmp.setRoleId(1L);
        fakeEmp.setRoleId(7L); //
        PrincipalDetails principalDetails = new PrincipalDetails(fakeEmp, authorities);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, authorities);

        Model model = mock(Model.class); // Mock으로 Model 객체 생성
        model.addAttribute("emp", fakeEmp);
        // When
        vacationService.insertVacation(fakeVacation,diffDays, fakeEmp.getEmpId());
        // Then
        assertEquals(expectedViewName, "redirect:/mypage/my-vac-list");
        verify(vacationService, times(1)).insertVacation(fakeVacation,diffDays, fakeEmp.getEmpId());
    }

//    @Test
//    public void vacationDetail() {
//        // Given
//        String expectedViewName = "/vacation/vacation-detail";
//        Emp fakeEmp = new Emp();
//        fakeEmp.setEmpId(152L); //emp
//
//        Vacation fakeVacation = new Vacation();
//        fakeVacation.setVacId(80L);
//        fakeVacation.setVacType("병가");
//        fakeVacation.setAdmId(5L);
//
//        Emp fakeAdminEmp = new Emp();
//        fakeAdminEmp.setEmpId(fakeVacation.getAdmId()); //admin
//
//        // hrMapper의 필요한 동작을 모킹합니다.
//        when(hrService.selectEmpInfo(fakeEmp.getEmpId())).thenReturn(fakeEmp);
//        System.out.println(fakeEmp);
//        List<String> auth = Arrays.asList("EMPLOYEE", "ACTIVE");
//        when(hrMapper.selectRoleName(fakeEmp.getEmpSabun())).thenReturn(auth);
//
//        when(hrService.selectEmpInfo(fakeVacation.getAdmId())).thenReturn(fakeAdminEmp);
//        when(vacationService.vacationDetail(fakeVacation.getVacId())).thenReturn(fakeVacation);
//
//        List<GrantedAuthority> authorities = auth.stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//
//        fakeEmp.setAuthorities(authorities);
//        fakeEmp.setRoleName(authorities.toString());
//        fakeEmp.setRoleId(1L);
//        fakeEmp.setRoleId(7L); //
//        PrincipalDetails principalDetails = new PrincipalDetails(fakeEmp, authorities);
//        Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, authorities);
//
//        Model model = mock(Model.class); // Mock으로 Model 객체 생성
//        model.addAttribute("emp", fakeEmp);
//        // When
//        vacationService.vacationDetail(fakeVacation.getVacId());
//        // Then
//        assertEquals(expectedViewName, "/vacation/vacation-detail");
//        verify(vacationService, times(1)).vacationDetail(fakeVacation.getVacId());
//
//    }
    @Test
    public void testApproveVacationRequest() {
        // Arrange
        Map<String, Object> requestData = new HashMap<>();
        AlarmDTO alarmDTO = new AlarmDTO();

        Long vacationId = 80L;
        String vacStatus = "승인대기";
        Long receiver = 5L;
        requestData.put("vacationId", vacationId);
        requestData.put("vacStatus", vacStatus);
        requestData.put("receiver", receiver);

        alarmDTO.setNotiContent("신청하신  " + vacationId + "번 휴가가 승인되었습니다!");
        alarmDTO.setNotiUrl("/mypage/my-vac-list");
        alarmDTO.setNotiType("휴가정보");
        alarmDTO.setEmpId(receiver);

        // Mocking !!
        doNothing().when(vacationService).approveVacationRequest(vacationId, vacStatus, receiver);
        //when(alarmMapper.insertAlarm(alarmDTO)).thenReturn(1); // Mocking the insertAlarm() method

        // Act
        ResponseEntity<String> response = vacationController.approveVacationRequest(requestData);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("성공", response.getBody());

        verify(vacationService, times(1)).approveVacationRequest((Long) requestData.get("vacationId"), requestData.get("vacStatus").toString(), (Long) requestData.get("receiver"));
    }

}




