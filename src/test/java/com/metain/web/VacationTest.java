package com.metain.web;

import com.metain.web.controller.VacationController;
import com.metain.web.domain.Vacation;
import com.metain.web.dto.VacationListDTO;
import com.metain.web.mapper.VacationMapper;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VacationTest {
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
