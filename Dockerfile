#베이스이미지
FROM tomcat:9.0.48-jdk11-openjdk

# wkhtmltopdf 설치를 위한 의존성 패키지 설치
RUN apt-get update && apt-get install -y \
    wget \
    fontconfig \
    libfreetype6 \
    libxrender1 \
    xfonts-base \
    xfonts-75dpi

# wkhtmltopdf 다운로드 및 설치
RUN wget -q -O wkhtmltopdf.deb https://github.com/wkhtmltopdf/wkhtmltopdf/releases/download/0.12.6-1/wkhtmltox_0.12.6-1.bionic_amd64.deb \
    && dpkg -i wkhtmltopdf.deb \
    && apt-get -f install \
    && rm wkhtmltopdf.deb


# 젠킨스 빌드로 생성된 war 파일을 도커 컨테이너에 추가
ADD target/web-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/

ADD server.xml /usr/local/tomcat/conf/

# 컨테이너 외부에서 사용하는 포트 지정
EXPOSE 9000


#start tomcat(Tomcat 실행 시 웹 애플리케이션을 자동으로 로드)
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]

# mavem 버전정보, pom.xmml, build되면 파일이름 뭐로 되는지,
# 빌드될때 무슨 파일들 빌드 되도록 되어있는지 check 
