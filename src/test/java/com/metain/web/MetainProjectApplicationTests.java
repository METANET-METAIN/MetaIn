package com.metain.web;

import com.metain.web.domain.Emp;
import com.metain.web.service.HrService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MetainProjectApplicationTests {
@Autowired
private HrService empSer;
    @Test
    void contextLoads() {
    }

    @org.junit.Test
    void t(){
        List<Emp> empList=empSer.selectAll();
        System.out.println(empList);
    }

}
