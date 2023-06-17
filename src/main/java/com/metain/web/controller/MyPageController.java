package com.metain.web.controller;

import com.metain.web.domain.*;
import com.metain.web.dto.MyCertDTO;
import com.metain.web.dto.MyVacDTO;
import com.metain.web.service.HrService;
import com.metain.web.service.MyPageService;
import com.metain.web.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    private MyPageService myPageService;
    @Autowired
    private VacationService vacationService;
    @Autowired
    private HrService hrService;

    @GetMapping("/update-mypage")
    public String updateMyPage() {
        return "/mypage/update-mypage";
    }


    //재직증명서 리스트
    @GetMapping("/my-empCert")
    @ResponseBody
    public List<EmpCert> selectMyEmpCert(EmpCert empCert) {
        empCert.setEmpId(4L);
        return myPageService.selectMyEmpCert(empCert);
    }

    //경력증명서 리스트
    @GetMapping("/my-experCert")
    @ResponseBody
    public List<ExperienceCert> selectMyExperCert(ExperienceCert experienceCert) {
        experienceCert.setEmpId(4L);
        return myPageService.selectMyExperCert(experienceCert);
    }

    //퇴직증명서 리스트
    @GetMapping("/my-retireCert")
    @ResponseBody
    public List<RetireCert> selectMyRetCert(RetireCert retireCert) {
        retireCert.setEmpId(4L);
        return myPageService.selectMyRetCert(retireCert);
    }


    //파일 다운로드
    @ResponseBody
    @Transactional
    @PostMapping("/downloadCert/{certId}/{certSort}")
    public ResponseEntity<Resource> downloadCert(@PathVariable("certId") Long certId, @PathVariable("certSort") String certSort) throws IOException {

        System.out.println("뷰단에서 값잘받아왔나 확인 :  certid랑 certsort " + certId + " , " + certSort);

        //받아온 certId를 이용해서 파일이름 을 얻어오기
        String filename = myPageService.getCertFilename(certId, certSort) + ".pdf"; //다운로드할 PDF 파일명 - 디지털서명된 파일이름 empcert같은 객체에서 가져오기

        System.out.println("증명서파일이름 가져왔나 확인 : " + filename);
        //Path filepath = Paths.get("/src/main/resources/static/certPdfFile", filename); // PDF 파일의 경로
        //Resource fileResource = new PathResource(filepath);
        Resource fileResource = new ClassPathResource("static/certPdfFile/" + filename);

        //둘중 뭐가 낫지? 뭔차이?
        // 디지털 서명이 포함된 PDF 파일을 PathResource로 생성
        //Resource fileResource = new FileSystemResource(filePath);
        //Resource fileResource = new PathResource(filePath);


        //다운로드한번 받으면 issueStatus 값 1로 업데이트 서비스메소드
        //myPageService.updateIssueStatus(certId,certSort);

        if (fileResource.exists()) {

            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(fileResource);
        } else {
            // 파일이 존재하지 않을 경우 에러 처리 로직
            // 예를 들어 404 Not Found 응답을 반환하거나 다른 방식으로 처리 가능
            return ResponseEntity.notFound().build();
        }
    }//downloadCert


    @GetMapping("/my-vac")
    @ResponseBody
    public List<MyVacDTO> selectMyVacList(@ModelAttribute MyVacDTO myVacDTO) {
        myVacDTO.setEmpId(5L); //일단 시큐리티 구현전까지 하드코딩 나중에 삭제할거임
        return myPageService.selectMyVacList(myVacDTO);
    }

    @GetMapping("/my-vac-list")
    public String myVacList(Long empId, Model model) {
        empId = 5L;
        List<MyVacDTO> myList = myPageService.myVacList(empId);
        model.addAttribute("vacList", myList);
        return "/mypage/my-vac-list";
    }

    @GetMapping("/my-vac-detail/{vacationId}")
    public String myVacDetail(@PathVariable("vacationId") Long vacationId, Model model) {
        if (vacationId == null) {
            new ModelAndView("redirect:/vacation/vacation-list");// vacationId가 없을 경우 기본 페이지로 리다이렉션
        }
        Vacation vac = vacationService.vacationDetail(vacationId);
        //신청인 정보
        Emp emp = hrService.selectEmpInfo(vac.getEmpId());
        //관리자 정보
        Emp admin = hrService.selectEmpInfo(vac.getAdmId());
        //총 사용날짜 구하기
        java.util.Date startDate = new java.util.Date(vac.getVacStartDate().getTime());
        java.util.Date endDate = new java.util.Date(vac.getVacEndDate().getTime());

        long diff = endDate.getTime() - startDate.getTime();
        int daysDiff = (int) (diff / (24 * 60 * 60 * 1000) + 1);

        model.addAttribute("vac", vac);
        model.addAttribute("emp", emp);
        model.addAttribute("admin", admin);
        model.addAttribute("diff", daysDiff);
        return "/mypage/my-vac-detail";
    }

    @PostMapping(value = "/cancelVacationRequest")
    @ResponseBody
    public ResponseEntity<String> cancelVacationRequest(@RequestBody Map<String, Object> requestData) {
        Long vacId = Long.parseLong(requestData.get("vacationId").toString());
        Long empId = Long.parseLong(requestData.get("empId").toString());
        String vacStatus = requestData.get("vacStatus").toString();

        vacationService.cancelVacationRequest(vacId, empId, vacStatus);
        return ResponseEntity.ok("성공");
    }
}
