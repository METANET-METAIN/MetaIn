package com.metain.web.mypageTest;

import com.metain.web.controller.MyPageController;
import com.metain.web.controller.VacationController;
import com.metain.web.domain.Emp;
import com.metain.web.dto.VacationListDTO;
import com.metain.web.mapper.AlarmMapper;
import com.metain.web.mapper.HrMapper;
import com.metain.web.mapper.VacationMapper;
import com.metain.web.service.HrService;
import com.metain.web.service.MemberService;
import com.metain.web.service.MyPageService;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MypageControllerTest {
    @Mock
    private HrMapper hrMapper;
    @Mock
    private HrService hrService;
    @Mock
    private VacationService vacationService;
    @Mock
    private AlarmMapper alarmMapper;
    @InjectMocks
    private MyPageController myPageController;
    @Autowired
    private MockMvc mockMvc; // MockMvc 선언
    @Mock
    private MyPageService myPageService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(myPageController).build(); // HomeController를 MockMvc에 설정
    }

    @Test
    @DisplayName("업데이트-마이페이지 ")
    @WithMockUser(username = "152L", roles = "EMPLOYEE")
    public void updateMyPageTest() throws IOException {
        // Given
        String expectedViewName = "redirect:/index";
        Emp fakeEmp = new Emp();
        fakeEmp.setEmpId(152L);
        fakeEmp.setEmpAddr("경기도 의정부시 용민로 21번길 32");
        fakeEmp.setEmpZipcode("12345");
        fakeEmp.setEmpDetailAddr("201동");
        fakeEmp.setEmpPhone("010-1111-1111");
        fakeEmp.setEmpPwd("19971111");
        MultipartFile file = new MockMultipartFile("1.jpg",new byte[]{});
        fakeEmp.setEmpProfile(file.toString());
        //mocking
        doNothing().when(myPageService).updateMy(fakeEmp,file);

        // when
        myPageService.updateMy(fakeEmp,file);
        // then
        verify(myPageService,times(1)).updateMy(fakeEmp,file);
    }
}
