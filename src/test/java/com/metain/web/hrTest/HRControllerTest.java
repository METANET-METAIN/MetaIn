package com.metain.web.hrTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.metain.web.controller.HrController;
import com.metain.web.domain.Emp;
import com.metain.web.domain.NewEmp;
import com.metain.web.domain.PrincipalDetails;
import com.metain.web.service.HrService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collection;
import java.util.Collections;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HRControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Mock
    HrController hrController;

    @Mock
    HrService hrService;

    @Before
    public void setup() {
        System.out.println(1);
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(hrController).build();
    }

    @Test
    public void insertNewEmpTest() throws Exception {
        // given
        String expectedViewName = "redirect:/hr/new-emp-list";
        NewEmp newDto = new NewEmp();
        newDto.setNewName("suhyun");
        newDto.setNewPhone("010-1111-1111");
        newDto.setNewAddr("경기 의정부시 가금로 29");
        newDto.setNewZipcode("11611");
        newDto.setNewDetailAddr("301동");
        newDto.setNewDept("개발 1팀");
        newDto.setNewBirth("1997-01-01");
        newDto.setNewGrade("EMPLOYEE");

        when(hrService.insertNewEmp(newDto)).thenReturn(1);


//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/insert-new-emp")
//                .flashAttr("emp", newDto);
//        mockMvc.perform(request);
//
//        // when
//        hrService.insertNewEmp(newDto);
        // when
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/hr/insert-new-emp")
                        .param("newName", newDto.getNewName())
                        .param("newPhone", newDto.getNewPhone())
                        .param("newAddr", newDto.getNewAddr())
                        .param("newZipcode", newDto.getNewZipcode())
                        .param("newDetailAddr", newDto.getNewDetailAddr())
                        .param("newDept", newDto.getNewDept())
                        .param("newBirth", newDto.getNewBirth())
                        .param("newGrade", newDto.getNewGrade()))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
//                .andExpect(MockMvcResultMatchers.redirectedUrl("/hr/new-emp-list"));

        // then
        assertEquals(expectedViewName, result.getResponse().getRedirectedUrl());
        verify(hrService, times(1)).insertNewEmp(newDto);
//        assertEquals(expectedViewName, "redirect:/hr/new-emp-list");
//        mockMvc.perform(request)
//                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//                .andExpect(MockMvcResultMatchers.redirectedUrl("/hr/new-emp-list"));
    }

    @Test
    @DisplayName("selectEmpInfo테스트")
    public void selectEmpInfoTest() {
//
//            // given
//            Long empId = 1L;
//            Emp expectedEmp = new Emp();
//            expectedEmp.setEmpId(empId);
//            expectedEmp.setEmpName("John Doe");
//            // Set other properties of expectedEmp
//
//            when(hrMapper.selectEmpInfo(empId)).thenReturn(expectedEmp);
//
//            // when
//            Emp actualEmp = empService.selectEmpInfo(empId);
//
//            // then
//            assertEquals(expectedEmp, actualEmp);
//        }
    }

    @Test
    public void home_ReturnsIndexPage() throws Exception {
        // Given
        Long empId = 5L;
        String expectedViewName = "index";

        Emp fakeEmp = new Emp();
        fakeEmp.setEmpId(152L);
        fakeEmp.setEmpDetailAddr("호서");
        fakeEmp.setEmpZipcode("1322");
        fakeEmp.setEmpVac(10);
        fakeEmp.setEmpStatus("ACTIVE");
        fakeEmp.setEmpAddr("호서");
        fakeEmp.setEmpBirth(new java.util.Date(19980915));
        fakeEmp.setEmpSabun("11111");
        fakeEmp.setEmpName("아무개");
        fakeEmp.setEmpPwd("123");
        fakeEmp.setEmpEmail("@@.com");
        fakeEmp.setEmpDept("개발 1팀");
        fakeEmp.setEmpGrade("EMPLOYEE");
        fakeEmp.setEmpFirstDt(new java.util.Date(19980915));
        fakeEmp.setEmpLastDt(new java.util.Date(19980915));

        PrincipalDetails principalDetails = new PrincipalDetails(fakeEmp, Collections.singleton(new SimpleGrantedAuthority("ROLE_EMPLOYEE")));
        Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, "password", principalDetails.getAuthorities());

        // When
//        mockMvc.perform(get("/index")
//                        .with(authentication(authentication)))
//                .andExpect(status().isOk())
//                .andExpect(view().name(expectedViewName))
//                .andExpect(model().attributeExists("emp"));

        // Then
        // Additional assertions if needed
    }


}
