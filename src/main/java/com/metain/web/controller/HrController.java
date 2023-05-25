package com.metain.web.controller;

import com.metain.web.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HrController {
    @Autowired
    private HrService hrService;

    @RequestMapping("/hr/emp-list")
    public String selectAll(Model model){
//        List<Emp> empList = hrService.empList();
//        model.addAttribute("list",empList);
        return "hr/emp-list";
    }


}
