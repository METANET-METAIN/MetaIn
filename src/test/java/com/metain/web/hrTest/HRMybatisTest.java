package com.metain.web.hrTest;

import com.metain.web.dto.VacationListDTO;
import com.metain.web.mapper.VacationMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class HRMybatisTest {
    @Autowired
    private VacationMapper vacMapper;
   
    public void selectListByDept(){
        LocalDate today = LocalDate.now();
        String empDept="IT";
        List<VacationListDTO> currMonthVac=vacMapper.selectListByDept(empDept,today);
        System.out.println(currMonthVac);
    }
}
