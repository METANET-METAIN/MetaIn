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


import javax.servlet.ServletContext;
import java.io.*;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Base64;

@Service
public class CertificationServiceImpl implements CertificationService {


    @Autowired
    private CertificationMapper certificationMapper;

    @Autowired
    private AwsS3Service awsS3Service;

    @Autowired
    private MyPageMapper myPageMapper;

    private final HrService hrService;

    @Autowired
    private ServletContext servletContext;


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
        }//if

        return empInfolist;
    }//getEmpInfoList


    //재직증명서 발급신청 - 정보입력
    //재직증명서 신청시 입력정보 추가
    //재직증명서 신청시 발급내역 추가
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
        //certificationMapper.insertIssue(empCert);

        // SELECT 결과 처리
        if (certlist == null) {
            // 결과가 없는 경우 처리
            System.out.println("해당하는 EmpCert정보가 없습니다.");
            return null;
        } else {
            // 필요한 작업 수행
            System.out.println("트랜잭션처리한 함수 확인 : " + certlist);
            return certlist;
        }//if

    }//applyAndSelectEmpCert

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
        }//if

    }//applyAndSelectExperCert


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
        }//if
    }//applyAndSelectRetireCert


    @Override
    public void makeCertPdf(ImageRequestData request) throws IOException {


        // 이미지 데이터를 Base64로 디코드하여 PDF로 변환
        byte[] imageBytes = Base64.getDecoder().decode(request.getImageData().split(",")[1]);

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

            // PDF 파일을 바이트 배열로 변환
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            byte[] pdfBytes = baos.toByteArray();

            // S3에 PDF 파일 업로드
            String objectKey = "certification/converted.pdf";
            awsS3Service.updateFileInS3(pdfBytes, objectKey);

            //local용 경로
            //String filePath = "src/main/resources/static/certPdfFile/";

        }//try
    }//makeCertPdf

    @Override
    public void signPdf(String filename) throws Exception {

        System.out.print(" 디지털서명함수 check 1 filename도 같이 잘넘어왔나?" + filename + " /");

        //s3가져오는 방식
        String pfxObjectKey = "certificate.pfx";
        String pdfObjectKey = "certification/converted.pdf";
        String signObjectKey = "certification/metain-sign-Image.png";

        //String mountPath = "/metainfiles/";- 배포할때

        try {
            InputStream pfxInputStream = awsS3Service.getFileInputStreamFromS3(pfxObjectKey);

            InputStream pdfInputStream = awsS3Service.getFileInputStreamFromS3(pdfObjectKey);

            InputStream signInputStream = awsS3Service.getFileInputStreamFromS3(signObjectKey);


            // 서명된 파일을 저장할 OutputStream 생성
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            // 입력 스트림을 사용하여 작업 수행
            Signature signature = new Signature(pdfInputStream);

            System.out.print(" / 디지털서명함수 check 2" + signature);

            // InputStream을 사용하여 작업 수행
            DigitalSignOptions options = new DigitalSignOptions(pfxInputStream);
            System.out.print(" / 디지털서명함수 check 3" + options);

            options.setPassword("12345678900");
            options.setVisible(true);
            //options.setImageFilePath("/usr/local/tomcat/webapps/web-0.0.1-SNAPSHOT/WEB-INF/classes/static/certPdfFile/metain-sign-Image.png");
            options.setImageStream(signInputStream);
            options.setWidth(80);
            options.setHeight(80);
            options.setLeft(370);
            options.setTop(650);
            options.setPageNumber(1);
            System.out.println("지장크기확인 : 높이 :" + options.getHeight() + " , 넓이 : " + options.getWidth() + "페이ㅣ지: " + options.getPageNumber() + "페이지정보:" + options.getAllPages());
            System.out.println("사인 확장자확인" + options.getExtensions() + "/ 시그니처타입 : " + options.getSignatureType());
            System.out.println(" / 디지털서명함수 check 4" + options);
            System.out.println(" 디지털서명함수 check 5 / ");

            // 파일에 문서 서명
            //signature.sign(filePath + filename, options);

            // 파일에 문서 서명 및 결과를 outputStream에 저장
            signature.sign(outputStream, options); //저장할파일객체, 사인옵션 파라미터

            //디지털서명된 cert를 s3에 업로드하는 메소드 호출
            byte[] signedBytes = outputStream.toByteArray();
            awsS3Service.uploadCertToS3(signedBytes, filename);

            System.out.print(" 디지털서명함수 check 6 FINAL / ");
        } catch (Exception e) {
            System.out.print("에러기록 : " + e.toString());
            throw new GroupDocsSignatureException(e.toString());
        }//try


    }//signPdf


}