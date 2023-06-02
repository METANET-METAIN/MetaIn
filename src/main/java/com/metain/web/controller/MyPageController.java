package com.metain.web.controller;

import com.metain.web.dto.MyCertListDTO;
import com.metain.web.dto.MyVacDTO;
import com.metain.web.service.MyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    private MyPageService myPageService;

    @GetMapping("/update-mypage")
    public String updateMyPage() {
        return "/mypage/update-mypage";
    }

    @RequestMapping("/my-cert-list")
    public String selectIssueAll(Model model) {
        List<MyCertListDTO> list = myPageService.selectIssueAll();

        model.addAttribute("issueList", list);
        return "/mypage/my-cert-list";
    }

    @GetMapping("/my-vac")
    @ResponseBody
    public List<MyVacDTO> selectMyVacList(@ModelAttribute MyVacDTO myVacDTO) {
        myVacDTO.setEmpId(5L); //일단 시큐리티 구현전까지 하드코딩 나중에 삭제할거임
        return myPageService.selectMyVacList(myVacDTO);
    }
}
