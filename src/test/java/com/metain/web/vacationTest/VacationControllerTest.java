//package com.metain.web.vacationTest;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//
//import com.groupdocs.signature.internal.c.a.i.internal.gJ.F;
//import com.metain.web.controller.VacationController;
//import com.metain.web.domain.Emp;
//import com.metain.web.domain.PrincipalDetails;
//import com.metain.web.dto.VacationListDTO;
//import com.metain.web.service.HrService;
//import com.metain.web.service.VacationService;
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.ui.Model;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.sql.Date;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import static com.metain.web.domain.Role.EMPLOYEE;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//@AutoConfigureMockMvc
//public class VacationControllerTest {
//    private MockMvc mockMvc;
//    @Autowired
//    private WebApplicationContext context;
//    @Mock
//    private VacationService vacationService;
//    @Mock
//    private HrService hrService;
//
//
//    @InjectMocks
//    private VacationController vacationController;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//
//    }
//
//
//    @Test
//    public void vacationListTest() throws Exception {
//        // 가짜 데이터
//        List<VacationListDTO> fakeList = new ArrayList<>();
//        VacationListDTO fakeDTO = new VacationListDTO(30L,"김사원","개발 1팀","EMPLOYEE","일반(연차)",new Date(2023-05-30),new Date(2023-05-30),new Date(2023-05-30),"요청취소");
//
//        fakeList.add(fakeDTO);
//        // ... 필요한 가짜 데이터 추가
//
//        Emp fakeEmp = new Emp();
//        fakeEmp.setEmpId(152L);
//        fakeEmp.setEmpDetailAddr("호서");
//        fakeEmp.setEmpZipcode("1322");
//        fakeEmp.setEmpVac(10);
//        fakeEmp.setEmpStatus("ACTIVE");
//        fakeEmp.setEmpAddr("호서");
//        fakeEmp.setEmpBirth(new java.util.Date(19980915));
//        fakeEmp.setEmpSabun("11111");
//        fakeEmp.setEmpName("아무개");
//        fakeEmp.setEmpPwd("123");
//        fakeEmp.setEmpEmail("@@.com");
//        fakeEmp.setEmpDept("개발 1팀");
//        fakeEmp.setEmpGrade("EMPLOYEE");
//        fakeEmp.setEmpFirstDt(new java.util.Date(19980915));
//        fakeEmp.setEmpLastDt(new java.util.Date(19980915));
//
//
//        // ... 필요한 가짜 데이터 추가
//
//        // Mock 객체에 대한 동작 지정
//        when(vacationService.selectAllList()).thenReturn(fakeList);
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("EMPLOYEE"));
//        PrincipalDetails principalDetails = new PrincipalDetails(fakeEmp, authorities);
//
//        // ... PrincipalDetails에 필요한 가짜 데이터 설정
//
//        Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        when(hrService.selectEmpInfo(152L)).thenReturn(fakeEmp);
//
//        // 테스트할 메서드 실행
//        mockMvc.perform(get("/vacation/list").principal(authentication))
//                .andExpect(status().isOk())
//                .andExpect(view().name("/vacation/vacation-list"))
//                .andExpect(model().attribute("vacList", fakeList))
//                .andExpect(model().attribute("emp", fakeEmp));
//
//        // 결과 확인
//        verify(vacationService, times(1)).selectAllList();
//        verify(hrService, times(1)).selectEmpInfo(152L);
//    }
//}