package com.metain.web.mypageTest;

import com.metain.web.controller.VacationController;
import com.metain.web.dto.VacationListDTO;
import com.metain.web.mapper.VacationMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MypageControllerTest {
    @Autowired
    private VacationController vacController;
    @Autowired
    private VacationMapper vacMapper;
    @Test
    public void list(){
        //List<Vacation> vacationList =vacController.vacationList();
        }
    @Test
    public void selectListByDept(){
        LocalDate today = LocalDate.now();
        String empDept="IT";
        List<VacationListDTO> currMonthVac=vacMapper.selectListByDept(empDept,today);
        System.out.println(currMonthVac);
    }
}
