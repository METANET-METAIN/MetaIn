package com.metain.web.hrTest;
import com.metain.web.domain.Emp;
import com.metain.web.domain.PrincipalDetails;
import com.metain.web.mapper.HrMapper;
import com.metain.web.security.PrincipalService;
import com.metain.web.service.HrService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@Slf4j
@SpringBootTest
public class PrincipalServiceTest {

    private MockMvc mvc;

    @InjectMocks
    private PrincipalService principalService;

    @MockBean
    private PrincipalDetails principalDetails;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Mock
    private HrMapper hrMapper;

    @Mock
    private HrService hrService;

    @MockBean
    private MockHttpSession mockSession;
//
//
//    private final String EMP_ID = "214";
//    private final String EMP_SABUN = "20230214";
//    private String EMP_PWD = "19970921";
//
//
    @Autowired
    private WebApplicationContext context;
//
//
//
////    @MockBean
////    private PrincipalDetails principalDetails;
//
//
//



//    @BeforeEach
//    public void setUp() {
//        mvc = MockMvcBuilders
//                .webAppContextSetup(this.context)
//                .apply(springSecurity())
//                .build();
//        MockitoAnnotations.openMocks(this);
//    }

    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("로그인")
    @WithMockUser(username = "152L", roles = {"EMPLOYEE", "ACTIVE"})
    void login() {
        // given
        List<String> auth = Arrays.asList("EMPLOYEE", "ACTIVE");
        Emp empDto = new Emp();
        empDto.setEmpSabun("20230081");
        empDto.setEmpId(152L);

//        String roles = empDto.getRoleName().substring(1, empDto.getRoleName().length()-1);
        System.out.println(empDto);

//        empDto.setEmpPwd(bCryptPasswordEncoder.encode(empPwd)); // 비밀번호 암호화

//        when(hrMapper.selectEmpInfo(empDto.getEmpId())).thenReturn(empDto);
//        System.out.println(empDto);
        when(hrMapper.login(empDto.getEmpSabun())).thenReturn(empDto);
        System.out.println(empDto);
        when(hrMapper.selectRoleName(empDto.getEmpSabun())).thenReturn(auth);
        List<GrantedAuthority> authorities = auth.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        empDto.setAuthorities(authorities);
        empDto.setRoleName(authorities.toString());
        empDto.setRoleId(1L);
        empDto.setRoleId(7L);

        PrincipalDetails principalDetails = new PrincipalDetails(empDto, authorities);

//        // MockMvc를 사용하여 컨트롤러 호출
//        mvc.perform(post("/loginEmp")
//                        .with(request -> {
//                            request.setMethod("POST");
//                            request.setSession(new MockHttpSession());
//                            return request;
//                        }))
//                .andExpect(status().isOk())
//                .andExpect(view().name("loginSuccessPage"));
//        hrMapper.selectEmpInfo(empDto.getEmpId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, authorities);
        Model model = mock(Model.class); // Mock으로 Model 객체 생성
        model.addAttribute("emp", empDto);


// when
        UserDetails userDetails = principalService.loadUserByUsername(empDto.getEmpSabun());


//        UserDetails userDetails = (UserDetails) hrMapper.login(empDto.getEmpSabun());


        // then
        assertNotNull(userDetails);
        assertTrue(userDetails instanceof PrincipalDetails);
        assertEquals(empDto.getEmpId(), ((PrincipalDetails) userDetails).getEmpId());
        assertEquals(auth.size(), userDetails.getAuthorities().size());
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            assertTrue(auth.contains(authority.getAuthority()));
        }

//        verify(hrMapper, times(1)).selectEmpInfo(empDto.getEmpId());
//        verify(hrMapper, times(1)).selectRoleName(empSabun);
        verify(hrMapper, times(1)).login(empDto.getEmpSabun());
    }



    @BeforeEach
    public void beforeEach() {
//        Emp emp = new Emp();
//        emp.setEmpSabun("20230081");
//        emp.setEmpPwd(bCryptPasswordEncoder.encode("19970921")); // 비밀번호 암호화
//        emp.setEmpPwd("");
//        when(hrMapper.login(emp.getEmpSabun())).thenReturn(emp);

        mvc = MockMvcBuilders.webAppContextSetup(context).build();
//        hrMapper.login(emp.getEmpSabun());
    }





//    @Test
//    @DisplayName("사용자 상세 정보 조회 성공")
//    @WithMockUser
//    void loadUserByUsername_Success() throws Exception {
//
//        when(hrMapper.login(anyString())).thenReturn(new Emp());
//
////        mvc.perform(get("/loginEmp")
////                        .param("username", "20230081")
////                        .param("password", "19970921")
////                        .with(csrf()))
////                .andExpect(status().isOk());
////
//
//        mvc.perform(post("/loginEmp")
//                        .param("username", "20230081")
//                        .param("password", "19970921")
//                        .with(csrf()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/index"))
//                .andExpect(authenticated().withUsername("20230081"));
//    }



//        // Mock 데이터 생성
//        String empSabun = "20230081";
//        Emp emp = new Emp();
//        emp.setEmpSabun(empSabun);
//        emp.setEmpPwd("19970921");



//        // Mock 동작 설정 when
//        when(hrMapper.login(EMP_SABUN)).thenReturn(emp);
//        when(hrMapper.selectRoleName(EMP_SABUN)).thenReturn(empRoles);

        // 테스트 실행
//        PrincipalDetails userDetails = (PrincipalDetails) principalService.(EMP_SABUN);

        // 결과 확인
//        assertNotNull(userDetails);
//        assertEquals(EMP_SABUN, userDetails.getUsername());
//        assertEquals(EMP_PWD, userDetails.getPassword());

//        List<GrantedAuthority> authorities = (List<GrantedAuthority>) userDetails.getAuthorities();
//        assertEquals(2, authorities.size());
//        assertEquals("ROLE_USER", authorities.get(0).getAuthority());
//        assertEquals("ROLE_ADMIN", authorities.get(1).getAuthority());
//    }
//
//    @Test
//    @DisplayName("사용자 상세 정보 조회 실패 - 사용자 없음")
//    void loadUserByUsername_UserNotFound() {
//        // Mock 데이터 생성
//        String empSabun = "20230209";
//
//        // Mock 동작 설정
//        when(hrMapper.login(empSabun)).thenReturn(null);
//
//        // 테스트 실행 및 예외 확인
//        assertThrows(UsernameNotFoundException.class, () -> {
//            principalService.loadUserByUsername(empSabun);
//        });
//    }

//    @Test
//    @DisplayName("사용자 상세 정보 조회 실패 - 역할 정보 없음")
//    void loadUserByUsername_RolesNotFound() {
//        // Mock 데이터 생성
//        String empSabun = "20230209";
//        Emp emp = new Emp();
//        emp.setEmpSabun(empSabun);
//        emp.setEmpPwd("password");
//
//        // Mock 동작 설정
//        when(hrMapper.login(empSabun)).thenReturn(emp);
//        when(hrMapper.selectRoleName(empSabun)).thenReturn(null);
//
//        // 테스트 실행
//        PrincipalDetails userDetails = (PrincipalDetails) principalService.loadUserByUsername(empSabun);
//
//        // 결과 확인
//        assertNotNull(userDetails);
//        assertEquals(empSabun, userDetails.getUsername());
//        assertEquals(emp.getEmpPwd(), userDetails.getPassword());
//
//        List<GrantedAuthority> authorities = (List<GrantedAuthority>) userDetails.getAuthorities();
//        assertEquals(0, authorities.size());
//    }
}
