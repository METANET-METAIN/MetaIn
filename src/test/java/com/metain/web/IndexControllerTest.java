package com.metain.web.indexTest;

import com.metain.web.controller.HomeController;
import com.metain.web.domain.Emp;
import com.metain.web.domain.PrincipalDetails;
import com.metain.web.mapper.HrMapper;
import com.metain.web.service.HrService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static junit.framework.TestCase.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IndexControllerTest {
    @Mock
    private HrMapper hrMapper;
    @Mock
    private HrService hrService;
    @InjectMocks
    private HomeController homeController;
    @Autowired
    private MockMvc mockMvc; // MockMvc 선언

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build(); // HomeController를 MockMvc에 설정

    }

    @Test
    public void home_ReturnsIndexPage() {
        // Given
        String expectedViewName = "index";
        Emp fakeEmp = new Emp();
        fakeEmp.setEmpId(152L);

        // hrMapper의 필요한 동작을 모킹합니다.
        when(hrService.selectEmpInfo(fakeEmp.getEmpId())).thenReturn(fakeEmp);
        System.out.println(fakeEmp);
        List<String> auth = Arrays.asList("EMPLOYEE","ACTIVE");
        when(hrMapper.selectRoleName(fakeEmp.getEmpSabun())).thenReturn(auth);

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
        String result = homeController.home(model,authentication);
        System.out.println(result);
        // Then
        assertEquals(expectedViewName, result);
        verify(hrService, times(1)).selectEmpInfo(fakeEmp.getEmpId());
        //verify(hrMapper, times(1)).selectRoleName(fakeEmp.getEmpSabun());
    }
}
