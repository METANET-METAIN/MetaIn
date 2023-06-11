package com.metain.web.scheduler;

import com.metain.web.domain.Emp;
import com.metain.web.service.HrService;
import com.metain.web.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@EnableScheduling
@Component
public class Scheduler {
    @Autowired
    private VacationService vacService;
    @Autowired
    private HrService hrService;


    @Scheduled(cron = "0 0 0 1 1 ?") //일월 일일
    //@Scheduled(cron = "0 * * * * ?") //테스트용 일분마다 ~
    //@Scheduled(cron = "0 0 * * * ?") //테스트용 한시간 마다!!
    public void autoAnnualUpdate() {
        List<Emp> empLists = hrService.selectAll();
        //System.out.println(empLists);
        Emp empInfo = new Emp();
        for(int i=0; i<empLists.size(); i++) {
            if(empLists.get(i).getEmpStatus() != "퇴직") { //활성화중 퇴사아닌애들
                if(empLists.get(i).getEmpFirstDt() != null) {
                    String StartWorkYear = (empLists.get(i).getEmpFirstDt()).toString();
                    StartWorkYear = StartWorkYear.substring(0, 4);
                    //System.out.println("입사년도  년도만 뺀거"+StartWorkYear);
                    int startWorkDat = Integer.parseInt(StartWorkYear);//년도만 가져옴
                    LocalDate now = LocalDate.now();
                    int todayYear = now.getYear();
                    //+1을 해준 이유는 신규입사자(해당 년도 입사자)가 1년차이기 때문 (1년 차 일땐 해당 없음)
                    System.out.println(empLists.get(i).getEmpId()+"는 몇년차인가"+(todayYear - startWorkDat));
                    if((todayYear - startWorkDat)+1 == 1) {
                        continue;
                    }else if((todayYear - startWorkDat)+1 == 2) {
                        empInfo.setEmpId(empLists.get(i).getEmpId());
                        empInfo.setEmpVac(15 );
                    }else if((todayYear - startWorkDat)+1 >= 3 && (todayYear - startWorkDat)+1 <= 4) {
                        empInfo.setEmpId(empLists.get(i).getEmpId());
                        empInfo.setEmpVac(16);
                    }else if((todayYear - startWorkDat)+1 >= 5 && (todayYear - startWorkDat)+1 <= 6) {
                        empInfo.setEmpId(empLists.get(i).getEmpId());
                        empInfo.setEmpVac(17);
                    }else if((todayYear - startWorkDat)+1 >= 7 && (todayYear - startWorkDat)+1 <= 8) {
                        empInfo.setEmpId(empLists.get(i).getEmpId());
                        empInfo.setEmpVac(18);
                    };
                    System.out.println("empvac 설정됐나 확인용 emp"+empInfo);
                    vacService.annualUpdate(empInfo);
                }
            }
        }
    }
    //신규입사자 !!!
    @Scheduled(cron = "0 0 0 1 * ?") //한달한번!!
    //@Scheduled(cron = "0 * * * * ?") // 테스트용 일분마다 ~
    //@Scheduled(cron = "*/20 * * * * *") // 20초마다 실행

    public void autoNewJoinCompanyAnnualUpdate() {
        List<Emp> empLists = hrService.selectAll();
        System.out.println(empLists);

        for (int i = 0; i < empLists.size(); i++) {
            Emp empInfo = empLists.get(i);
            if (empInfo.getEmpStatus() != "퇴직") {
                if (empInfo.getEmpFirstDt() != null) {
                    System.out.println("emp 정보 ㅜ" + empInfo );

                    //입사일
                    String usiStartWorkDat = (empInfo.getEmpFirstDt()).toString();
                    usiStartWorkDat = usiStartWorkDat.substring(0, 4); //입사 년도
                    int startWorkDat = Integer.parseInt(usiStartWorkDat);
                    LocalDate now = LocalDate.now();
                    int todayYear = now.getYear(); //현재 년도
                    if (todayYear == startWorkDat) { // 1년이 안됐다면
                        int empVacCount = empInfo.getEmpVac();
                        if (empVacCount < 12) {
                            empInfo.setEmpId(empLists.get(i).getEmpId());
                            empInfo.setEmpVac(empVacCount+ 1); // empVacCount 값을 업데이트
                            System.out.println(empVacCount);
                            vacService.annualUpdate(empInfo); // 업데이트된 객체를 전달
                        }
                    }
                }
            }
        }
    }


}
