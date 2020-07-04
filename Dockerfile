FROM maven:3-jdk-13
VOLUME /tmp
ENV PORT=8080
ENV PROFILE=prd
ENV DBURL=${DBURL}
ENV DBPORT=${DBPORT}
ENV DBDATABASENAME=${DBDATABASENAME}
ENV DBUSER=${DBUSER}
ENV DBPASSWORD=${DBPASSWORD}
ADD target/letscoinback-api.jar letscoinback-api.jar
EXPOSE $PORT
ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}",  "-jar",  "letscoinback-api.jar"]
