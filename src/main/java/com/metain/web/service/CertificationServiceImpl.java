package com.metain.web.service;


import com.groupdocs.signature.Signature;
import com.groupdocs.signature.domain.SignatureFont;
import com.groupdocs.signature.exception.GroupDocsSignatureException;
import com.groupdocs.signature.options.sign.DigitalSignOptions;
import com.metain.web.domain.*;
import com.metain.web.dto.CertInfoDTO;
import com.metain.web.dto.ImageRequestData;
import com.metain.web.mapper.CertificationMapper;
import com.metain.web.mapper.MyPageMapper;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Base64;

@Service
public class CertificationServiceImpl implements CertificationService {

    //Mapper인터페이스 의존성주입
    @Autowired
    private CertificationMapper certificationMapper;

    @Autowired
    private MyPageMapper myPageMapper;

    private final HrService hrService;


    @Autowired
    public CertificationServiceImpl(HrService hrService) {
        this.hrService = hrService;
    }


    //로그인한 사원 인사정보 조회
    @Override
    public Emp getEmpInfoList(Long empId) {

        Emp empInfolist = hrService.selectEmpInfo(empId);


        if (empInfolist == null) {
            return null;
        } else {

            String empGrade = empInfolist.getEmpGrade();
            if ("employee".equals(empGrade)) {
                empGrade = "사원";
            } else if ("assistant".equals(empGrade)) {
                empGrade = "대리";
            } else if ("manager".equals(empGrade)) {
                empGrade = "과장";
            } else if ("deputy".equals(empGrade)) {
                empGrade = "차장";
            } else {
                empGrade = "인사팀/관리자";
            }

            empInfolist.setEmpGrade(empGrade);
        }
        return empInfolist;
    }


    //재직증명서 발급신청 - 정보입력
    //재직증명서 신청시 입력정보 추가
    //재직증명서신청시 발급내역 추가
    @Override
    @Transactional
    public EmpCert applyAndSelectEmpCert(CertInfoDTO certInfoDTO) { //insert와 그 해당 insert한 행 가져오는 select 트랜젝션처리하기

        // 이전 쿼리 실행 후 자동으로 생성된 ID 값을 가져옴
        int lastInsertedId = certificationMapper.getLastEmpCertId();

        // +1을 수행하여 증가시킴
        int adjustedId = lastInsertedId + 1;

        // certInfoDTO 객체에 adjustedId 설정
        certInfoDTO.setAdjustedId(adjustedId);

        System.out.println("insert할 내용 확인 : " + certInfoDTO);
        certificationMapper.insertEmpCert(certInfoDTO);

        // 자동 생성된 키 값을 id 프로퍼티로 가져와서 CertId가지고 해당증명서내역 select하기
        System.out.println("generated id 확인 : " + certInfoDTO.getEmpCertId());
        Long certId = certInfoDTO.getEmpCertId();

        EmpCert certlist = certificationMapper.selectEmpCert(certId);
//        certificationMapper.insertIssue(empCert);

        // SELECT 결과 처리
        if (certlist == null) {
            // 결과가 없는 경우 처리
            System.out.println("해당하는 EmpCert정보가 없습니다.");
            return null;
        } else {
            // 필요한 작업 수행
            System.out.println("트랜잭션처리한 함수 확인 : " + certlist);
            return certlist;
        }
    }

    @Transactional
    public ExperienceCert applyAndSelectExperCert(CertInfoDTO certInfoDTO) {

        // 이전 쿼리 실행 후 자동으로 생성된 ID 값을 가져옴
        int lastInsertedId = certificationMapper.getLastExperCertId();

        // +1을 수행하여 증가시킴
        int adjustedId = lastInsertedId + 1;

        // certInfoDTO 객체에 adjustedId 설정
        certInfoDTO.setAdjustedId(adjustedId);


        certificationMapper.insertExperCert(certInfoDTO);

        System.out.println("generated id 확인 : " + certInfoDTO.getExperCertId());
        Long certid = certInfoDTO.getExperCertId();

        ExperienceCert certlist = certificationMapper.selectExperCert(certid);

        // SELECT 결과 처리
        if (certlist == null) {
            // 결과가 없는 경우 처리
            System.out.println("해당하는 ExperienceCert정보가 없습니다.");
            return null;
        } else {
            // 필요한 작업 수행
            System.out.println("certList확인 : " + certlist);
            return certlist;
        }


    }


    @Transactional
    public RetireCert applyAndSelectRetireCert(CertInfoDTO certInfoDTO) {

        // 이전 쿼리 실행 후 자동으로 생성된 ID 값을 가져옴
        int lastInsertedId = certificationMapper.getLastRetireCertId();

        // +1을 수행하여 증가시킴
        int adjustedId = lastInsertedId + 1;

        // certInfoDTO 객체에 adjustedId 설정
        certInfoDTO.setAdjustedId(adjustedId);


        certificationMapper.insertRetireCert(certInfoDTO);

        System.out.println("generated id 확인 : " + certInfoDTO.getRetireCertId());
        Long certId = certInfoDTO.getRetireCertId();

        RetireCert certlist = certificationMapper.selectRetireCert(certId);


        // SELECT 결과 처리
        if (certlist == null) {
            // 결과가 없는 경우 처리
            System.out.println("해당하는 RetireCert정보가 없습니다.");
            return null;
        } else {
            // 필요한 작업 수행
            return certlist;
        }
    }


    //증명서 생성 기능
    @Override
    public EmpCert getEmpCert(Long empId) {
        EmpCert list = certificationMapper.selectEmpCert(empId);
        if (list == null) {
            return null;
        }
        return list;
    }

    @Override
    public ExperienceCert getExperCert(Long empId) {
        ExperienceCert list = certificationMapper.selectExperCert(empId);
        if (list == null) {
            return null;
        }
        return list;
    }

    @Override
    public RetireCert getRetireCert(Long empId) {
        RetireCert list = certificationMapper.selectRetireCert(empId);
        if (list == null) {
            return null;
        }
        return list;
    }


    @Override
    public void makeCertPdf(ImageRequestData request) throws IOException {


        // 이미지 데이터를 Base64로 디코드하여 PDF로 변환
        byte[] imageBytes = Base64.getDecoder().decode(request.getImageData().split(",")[1]);

//        // 이미지 데이터를 BufferedImage로 변환
//        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
//        BufferedImage bufferedImage = ImageIO.read(bis);

        System.out.println("1!! pdf전환 컨트롤러에 요청 들어옴 , 이미지데이터 전달완료 :" + imageBytes);

        // PDF 생성
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // 이미지 크기 및 위치 설정
            float targetWidth = 1100; // A4용지 가로 너비 595
            float targetHeight = 1500; // A4용지 높이 842
            float imageWidth = request.getImageWidth();
            float imageHeight = request.getImageHeight();

            System.out.println("이미지크기 : x - " + imageWidth + " , y - " + imageHeight);
            float scale = Math.min(targetWidth / imageWidth, targetHeight / imageHeight);
            float offsetX = (targetWidth - imageWidth * scale) / 2 - 240;
            float offsetY = (targetHeight - imageHeight * scale) / 2 - 355;


            contentStream.drawImage(PDImageXObject.createFromByteArray(document, imageBytes, ""), offsetX, offsetY, imageWidth * scale, imageHeight * scale);
            contentStream.close();
            System.out.println("2!! pdf생성단계 ");

            //local용 경로
            //String filePath = "src/main/resources/static/certPdfFile/";

            //배포용 경로
            String filePath = "/metainfiles/";

            String fileName = "converted.pdf";
            // PDF 파일 저장
            document.save(filePath + fileName);
            document.close();
            System.out.println("3!! PDF 전환 및 저장 완료");
        }


    }

    @Override
    public void signPdf(String filename) throws Exception {


        try {

            System.out.print(" 디지털서명함수 check 1 filename도 같이 잘넘어왔나?"+filename +" /");


            //로컬용 경로
            //String filePath = "src/main/resources/static/certPdfFile/";

            //배포용 파일 경로
            String filePath = "/metainfiles/";

            Signature signature = new Signature(filePath + "converted.pdf");
            System.out.print(" / 디지털서명함수 check 2" + signature);

            //배포용 인증서경로
            //String certPath = "src/main/resources/certification/"; - 로컬용
            //String certPath = "/certification/";  //-배포용

            // 디지털 서명 옵션 정의
            //DigitalSignOptions options = new DigitalSignOptions(certPath + "metacert.pfx");

            String certPath = "src/main/resources/certification/";
            DigitalSignOptions options = new DigitalSignOptions(certPath + "metain.pfx");

            System.out.print(" / 디지털서명함수 check 3" + options);
            options.setPassword("12345678900");
            options.setVisible(true);
            options.setImageFilePath("src/main/resources/certPdfFile/metain-sign-Image.png");
            options.setWidth(80);
            options.setHeight(80);
            options.setLeft(370);
            options.setTop(650);
            options.setPageNumber(1);
            System.out.print("지장크기확인 : 높이 :" + options.getHeight() + " , 넓이 : " + options.getWidth());
            System.out.print(" / 디지털서명함수 check 4" + options);
            System.out.print(" 디지털서명함수 check 5 / ");

            // 파일에 문서 서명
            signature.sign(filePath + filename, options);
            System.out.print(" 디지털서명함수 check 6 FINAL / ");
        } catch (Exception e) {
            System.out.print("에러기록 : " + e.toString());
            throw new GroupDocsSignatureException(e.toString());
        }


    }//signPdf


}
