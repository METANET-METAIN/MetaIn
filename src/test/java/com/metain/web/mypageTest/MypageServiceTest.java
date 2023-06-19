package com.metain.web.mypageTest;

import com.metain.web.service.VacationService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MypageServiceTest {
    @Autowired
    private VacationService vacationService;

}
