#베이스이미지
FROM tomcat:9.0.48-jdk11-openjdk

WORKDIR /buil_file

# 젠킨스 빌드로 생성된 war 파일을 도커 컨테이너에 추가
ADD target/web-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/


# 컨테이너 외부에서 사용하는 포트 지정
EXPOSE 9000


#start tomcat(Tomcat 실행 시 웹 애플리케이션을 자동으로 로드)
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]

# mavem 버전정보, pom.xmml, build되면 파일이름 뭐로 되는지,
# 빌드될때 무슨 파일들 빌드 되도록 되어있는지 check 
