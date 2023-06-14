#베이스이미지
FROM tomcat:9.0.48-jdk11-openjdk

# 젠킨스 빌드로 생성된 war 파일을 도커 컨테이너에 추가
ADD target/web-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/

ADD server.xml /usr/local/tomcat/conf/


# 컨테이너 외부에서 사용하는 포트 지정
EXPOSE 9000


#start tomcat(Tomcat 실행 시 웹 애플리케이션을 자동으로 로드)
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]


