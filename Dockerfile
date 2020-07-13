FROM maven:3-jdk-13
VOLUME /tmp
ENV PORT=8080
ENV PROFILE=prd
ENV DBURL=${DBURL}
ENV DBPORT=${DBPORT}
ENV DBDATABASENAME=${DBDATABASENAME}
ENV DBUSER=${DBUSER}
ENV DBPASSWORD=${DBPASSWORD}
ENV GUID=${GUID}
ENV ADDRESS=${ADDRESS}
ENV WPAS=${WPASS}
ADD target/letscoinback-api.jar letscoinback-api.jar
EXPOSE $PORT
ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}",  "-jar",  "letscoinback-api.jar"]
CMD apt-get install npm && npm install -g blockchain-wallet-service  && nohup blockchain-wallet-service start --port 3000