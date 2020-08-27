FROM adoptopenjdk/openjdk11:ubi
COPY pandaz-bpm-server/target/pandaz-bpm-server-0.0.2-SNAPSHOT.jar /
CMD ["java", "-jar", "-Dspring.profiles.active=prod","pandaz-bpm-server-0.0.2-SNAPSHOT.jar"]
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo "Asia/Shanghai" > /etc/timezone