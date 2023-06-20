#베이스이미지
FROM tomcat:9.0.48-jdk11-openjdk

# 패키지 업데이트 및 필수 도구 설치
RUN apt-get update && apt-get install -y \
    wget \
    build-essential

# Ghostscript 다운로드 및 설치
RUN wget https://github.com/ArtifexSoftware/ghostpdl-downloads/releases/download/gs9533/ghostscript-9.53.3.tar.gz && \
    tar -zxvf ghostscript-9.53.3.tar.gz && \
    cd ghostscript-9.53.3 && \
    ./configure && \
    make && \
    make install

# 필요한 경우 Ghostscript 실행 파일의 경로를 환경 변수에 추가
ENV PATH="/usr/local/bin:${PATH}"


# 젠킨스 빌드로 생성된 war 파일을 도커 컨테이너에 추가
ADD target/web-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/

ADD server.xml /usr/local/tomcat/conf/




# 컨테이너 외부에서 사용하는 포트 지정
EXPOSE 9000
EXPOSE 443


#start tomcat(Tomcat 실행 시 웹 애플리케이션을 자동으로 로드)
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]


