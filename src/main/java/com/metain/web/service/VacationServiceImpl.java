package com.metain.web.service;

import com.metain.web.domain.Emp;
import com.metain.web.domain.Vacation;
import com.metain.web.dto.*;
import com.metain.web.mapper.AlarmMapper;
import com.metain.web.mapper.FileMapper;
import com.metain.web.mapper.HrMapper;
import com.metain.web.mapper.VacationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@EnableScheduling
public class VacationServiceImpl implements VacationService{
    @Autowired
    private VacationMapper vacMapper;
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private HrMapper hrMapper;
    @Autowired
    private AlarmMapper alarmMapper;
    @Autowired
    private AlarmService alarmService;
    @Autowired
    private AwsS3Service awsS3Service;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<VacationListDTO> selectAllList() {
        List<VacationListDTO> list = vacMapper.selectAllList();
        if (list==null) {
            return null;
        }
        return list;
    }

    @Override
    public VacationFileDTO vacationDetail(Long vacationId) {
        VacationFileDTO dbVacation=vacMapper.vacDetail(vacationId);
        if (dbVacation==null){
            return null;
        }
        return dbVacation;}

    @Override
    public List<VacationListDTO> requestList(String empDept) {
        List<VacationListDTO> list = vacMapper.requestList(empDept);
        if (list==null) {
            return null;
        }
        return list;
    }

    @Override
    public void approveVacationRequest(Long vacId,String vacStatus,Long receiver) {
        if(vacStatus.equals("승인대기")) {
            int result = vacMapper.approveVacationRequest(vacId, vacStatus);

            AlarmDTO alarmDTO=new AlarmDTO();
            alarmDTO.setNotiContent("신청하신  "+ vacId + "번 휴가가 승인되었습니다!");
            alarmDTO.setNotiUrl("/mypage/my-vac-list");
            alarmDTO.setNotiType("휴가정보");
            alarmDTO.setEmpId(receiver);
            alarmMapper.insertAlarm(alarmDTO);

            alarmService.send(receiver, AlarmResponse.comment("신청하신  "+ vacId + "번 휴가가 승인되었습니다!"));
            if (result == 0) {
                new Exception("에러");
            }
        }
    }
    @Override
    public void rejectVacationRequest(Long vacId,String vacStatus,Long receiver) {
        if(vacStatus.equals("승인대기")) {
            int result = vacMapper.rejectVacationRequest(vacId, vacStatus);
            AlarmDTO alarmDTO=new AlarmDTO();
            alarmDTO.setNotiContent("신청하신  "+ vacId + "번 휴가가 반려되었습니다!");
            alarmDTO.setNotiUrl("/mypage/my-vac-list");
            alarmDTO.setNotiType("휴가정보");
            alarmDTO.setEmpId(receiver);
            alarmMapper.insertAlarm(alarmDTO);

            alarmService.send(receiver, AlarmResponse.comment("신청하신  "+ vacId + "번 휴가가 반려되었습니다!"));


            if (result == 0) {
                new Exception("에러");
            }
        }
    }

    @Override
    public void cancelVacationRequest(Long vacId, Long empId,String vacStatus) {
        if(vacStatus.equals("승인대기")) {
            vacMapper.cancelVacationRequest(vacId, empId);

        }
    }

    @Override
    public void insertVacation(Vacation vacation,int diffDays,Long empId,Long admId) {
        vacMapper.requestVacation(vacation);
        logger.info("VacService/insertVacation-vacation",vacation);
        Emp requestEmp=hrMapper.selectEmpInfo(empId);

        AlarmDTO alarmDTO=new AlarmDTO();
        alarmDTO.setNotiContent("휴가가 신청되었습니다");
        alarmDTO.setNotiUrl("/mypage/my-vac-list");
        alarmDTO.setNotiType("휴가정보");
        alarmDTO.setEmpId(empId);
        alarmMapper.insertAlarm(alarmDTO);

        AlarmDTO adminAlarm=new AlarmDTO();
        alarmDTO.setNotiContent(requestEmp.getEmpName() +"님이 휴가를 신청하셨습니다");
        alarmDTO.setNotiUrl("/vacation/vacation-req-list");
        alarmDTO.setNotiType("신청 정보");
        alarmDTO.setEmpId(admId);
        System.out.println(adminAlarm);
        alarmMapper.insertAlarm(alarmDTO);

        alarmService.send(empId, AlarmResponse.comment("휴가가 정상적으로 신청되었습니다"));
        alarmService.send(admId, AlarmResponse.comment(requestEmp.getEmpName()+"님이 휴가를 신청하셨습니다."));

    }

    @Override
    public void insertAfterVacation(Vacation vacation, MultipartFile file,Long admId) throws IOException {
        Long empId = vacation.getEmpId();
        Emp emp=hrMapper.selectEmpInfo(empId);
        String sabun = emp.getEmpSabun();
        UUID uuid = UUID.randomUUID();

        File files = new File(file.getOriginalFilename());
        FileCopyUtils.copy(file.getBytes(), files);

        String originalImgName = file.getOriginalFilename();
        String extension = originalImgName.substring(originalImgName.lastIndexOf("."));

        String savedImgName = sabun + uuid.toString().substring(0, 5) + extension;
        String path="file";
        awsS3Service.uploadS3File(file,savedImgName,path);
        vacation.setFileName(savedImgName);

        logger.info("VacService/insertAfterVacation의 savedImgName",savedImgName);

        // 파일 정보 삽입
        FileDTO fileDTO = new FileDTO();
        fileDTO.setFileName(savedImgName);
        fileDTO.setEmpId(vacation.getEmpId());
        fileMapper.insertFile(fileDTO);
        logger.info("VacService/insertAfterVacation의 fileDTO",fileDTO);

        //인서트 되면 AUTOINCREAMENT 값 가져 와서 VAC에 넣기
        int fileId = fileMapper.getFileId();
        vacation.setFileId((long) fileId);
        //그로 VAC INSERT 시킨당
        vacMapper.insertAfterVacation(vacation);
        AlarmDTO alarmDTO=new AlarmDTO();
        alarmDTO.setNotiContent("휴가가 신청되었습니다");
        alarmDTO.setNotiUrl("/mypage/my-vac-list");
        alarmDTO.setNotiType("휴가정보");
        alarmDTO.setEmpId(empId);
        alarmMapper.insertAlarm(alarmDTO);

        AlarmDTO adminAlarm=new AlarmDTO();
        alarmDTO.setNotiContent(emp.getEmpName() +"님이 휴가를 신청하셨습니다");
        alarmDTO.setNotiUrl("/vacation/vacation-req-list");
        alarmDTO.setNotiType("신청 정보");
        alarmDTO.setEmpId(admId);
        System.out.println(adminAlarm);
        alarmMapper.insertAlarm(alarmDTO);

        alarmService.send(empId, AlarmResponse.comment("휴가가 신청되었습니다"));
        alarmService.send(admId, AlarmResponse.comment(emp.getEmpName()+"님이 휴가를 신청하셨습니다."));
    }

    @Override
    public List<VacationListDTO> selectListByDept(String empDept, LocalDate today) {
        today = LocalDate.now();
        List<VacationListDTO> currMonthVac=vacMapper.selectListByDept(empDept,today);
        return currMonthVac;
    }

    @Override
    public List<VacationListDTO> calendar(String empDept, LocalDate today) {
        today = LocalDate.now();
        List<VacationListDTO> calendar=vacMapper.calendar(empDept,today);
        return calendar;
    }

    @Override
    public int decreaseVacation(int selectedDays,Long empId) {
        int re=vacMapper.decreaseVacation(selectedDays, empId);
        return re;
    }

    @Override
    public int annualUpdate(Emp empInfo) {
        logger.info("VacService/annualUpdate 의 fileDTO",empInfo);
        return hrMapper.annualUpdate(empInfo);
    }

    @Override
    public List<AlarmDTO> alarmListAll(Long empId) {
        List<AlarmDTO>  alarmListAll=alarmMapper.alarmListAll(empId);
        if(alarmListAll==null){
            return null;
        }
        return alarmListAll;
    }

    @Override
    public List<VacationListDTO> todayVacation(String empDept) {
        List<VacationListDTO> list=vacMapper.todayVacation(empDept);
        if(list==null) {
            return null;
        }
        return list;
    }

    @Override
    public int increaseVacation(int selectedDays, Long empId) {
        int re=vacMapper.increaseVacation(selectedDays, empId);
        return re;
    }

    @Override
    public VacationWithoutFileDTO vacationDetailWithoutFile(Long vacationId) {
        VacationWithoutFileDTO dto=vacMapper.vacationDetailWithoutFile(vacationId);

        return dto;
    }


    @Scheduled(cron = "0 0 0 * * ?") //6080이 반려로 바껴야댐
    //@Scheduled(cron = "*/20 * * * * ?") //20 초
    //@Scheduled(cron = "0 * * * * ?") //테스트용 일분마다 ~
    public void autoReject() {
        List<VacationListDTO> list = vacMapper.selectAllList();
        LocalDate localDate = LocalDate.now();

        for (VacationListDTO vacation : list) {
            LocalDate vacStartDate = vacation.getVacStartDate().toLocalDate();
            LocalDate vacEndDate =vacation.getVacEndDate().toLocalDate();
            String vacStatus = vacation.getVacStatus();
            // startDate와 endDate 사이의 일수 차이 계산
            long daysBetween = ChronoUnit.DAYS.between(vacStartDate, vacEndDate);
            if (vacStatus.equals("승인대기") && (localDate.isEqual(vacStartDate) || localDate.isAfter(vacStartDate))) {
                // 반려하기
                int re = vacMapper.rejectVacationRequest(vacation.getVacId(), vacStatus);
                vacMapper.increaseVacation((int) daysBetween,vacation.getVacId());
                //logger.info("스케쥴러에의해 자동반려된 vacId ",vacation.getVacId());
                System.out.println("스케쥴러에 의해 자동 반려된 vacId= "+vacation.getVacId());
                System.out.println("스케쥴러에 의해 자동 반려된 vacId= "+(int) daysBetween);
            }
        }
    }
}
