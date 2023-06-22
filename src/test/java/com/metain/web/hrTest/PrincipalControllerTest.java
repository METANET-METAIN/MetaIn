package com.metain.web.hrTest;

import com.metain.web.config.WebConfig;
import com.metain.web.controller.VacationController;
import com.metain.web.domain.Emp;
import com.metain.web.domain.PrincipalDetails;
import com.metain.web.domain.Vacation;
import com.metain.web.dto.AlarmDTO;
import com.metain.web.dto.VacationListDTO;
import com.metain.web.mapper.AlarmMapper;
import com.metain.web.mapper.HrMapper;
import com.metain.web.security.CustomAuthenticationProvider;
import com.metain.web.security.PrincipalService;
import com.metain.web.service.HrService;
import com.metain.web.service.MemberService;
import com.metain.web.service.VacationService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PrincipalControllerTest {
    private MockMvc mvc;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private PrincipalService principalService;

    @InjectMocks
    private CustomAuthenticationProvider authenticationProvider;


    @Mock
    private PrincipalDetails principalDetails;
    @Mock
    private HrMapper hrMapper;

    @Mock
    private HrService hrService;

    @MockBean
    private MockHttpSession mockSession;
    private CsrfToken csrfToken;


    @Autowired
    private WebConfig webConfig;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(authenticationProvider).build();
        System.out.println("setup");
        MockitoAnnotations.openMocks(this);
        mockSession = new MockHttpSession();
        mockSession.setAttribute("empSabun", "20230081");

        HttpSessionCsrfTokenRepository csrfTokenRepository = new HttpSessionCsrfTokenRepository();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        csrfToken = csrfTokenRepository.generateToken(request); // request는 MockHttpServletRequest 객체입니다.
        csrfTokenRepository.saveToken(csrfToken, request, response);
    }




    @Test
    @DisplayName("로그인_컨트롤러")
    @WithMockUser(username = "20230209", roles = {"EMPLOYEE", "ACTIVE"})
    public void login() throws Exception {
        // Given

        String username = "20230209";
        String password = "19970921";
        Emp empDto = new Emp();
        empDto.setEmpId(152L);
        empDto.setEmpSabun(username);
        empDto.setEmpPwd(password); // 비밀번호 암호화
//        given();

        List<String> auth = Arrays.asList("EMPLOYEE","ACTIVE");
        List<GrantedAuthority> authorities = auth.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        empDto.setAuthorities(authorities);
        empDto.setRoleName(authorities.toString());
        empDto.setRoleId(1L);
        empDto.setRoleId(7L);


        // When
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        Authentication result = authenticationProvider.authenticate(authentication);



        // Then
        MvcResult mvcResult = mvc.perform(post("/loginEmp")
                .param("username", username)
                .param("password", password)
                .with(csrf().useInvalidToken())
                .session(mockSession))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"))
                .andExpect(authenticated().withUsername(username))
                .andReturn();

        HttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(principalService).loadUserByUsername(username);


//        // given
//        Emp empDto = new Emp();
//        empDto.setEmpSabun("20230081");
//        empDto.setEmpId(152L);
//        List<GrantedAuthority> auth = new ArrayList<>();
//        auth.add(new SimpleGrantedAuthority("EMPLOYEE"));
//        auth.add(new SimpleGrantedAuthority("ACTIVE"));
//        empDto.setAuthorities(auth);
//
//        System.out.println(empDto);
//        System.out.println(auth);
//        UserDetails userDetails =  principalService.loadUserByUsername(empDto.getEmpSabun());
//
//
//        // Mock 인증 객체 생성
//        String username = "20230081";
//        String password = "19970921";
//        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
//        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
//        securityContext.setAuthentication(authentication);
//        HttpSession session = new MockHttpSession();
//        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
//
//        // 인증 결과 설정
//        Authentication authResult = new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
//        when(authenticationProvider.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authResult);
//
//        // when & then
//        mvc.perform(post("/loginEmp")
//                        .param("username", username)
//                        .param("password", password)
//                        .with(csrf())
//                        .session((MockHttpSession) session))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/index"))
//                .andExpect(authenticated().withUsername(username));
//

//
//        // given
//        Emp empDto = new Emp();
//        empDto.setEmpId(152L);
//        empDto.setEmpSabun("202300081");
//        System.out.println(empDto);
//
//
//        List<String> auth = Arrays.asList("EMPLOYEE","ACTIVE");
//        List<GrantedAuthority> authorities = auth.stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//
//        PrincipalDetails principalDetails = new PrincipalDetails(empDto, authorities);
//
//
//
//
//
//        mvc.perform(post("/loginEmp")
//                        .param("username", "20230081")
//                        .param("password", "19970921")
//                        .with(csrf()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/index"))
//                .andExpect(authenticated().withUsername("20230081"));
//
//        // when
//
//        hrMapper.login(empDto.getEmpSabun());
//
//        // then
    }



}
