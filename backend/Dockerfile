FROM gradle:8.3.0-jdk17 as build-env

WORKDIR /app

COPY . .

RUN gradle war

FROM tomcat:latest

RUN rm -rf /usr/local/tomcat/webapps/

COPY --from=build-env /app/csim2126-web/build/libs/*.war ./webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
