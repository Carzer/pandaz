FROM adoptopenjdk/openjdk11:ubi
COPY pandaz-im-server/target/pandaz-im-server-0.0.2-SNAPSHOT.jar /
CMD ["java", "-jar", "-Dspring.profiles.active=prod","pandaz-im-server-0.0.2-SNAPSHOT.jar"]
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo "Asia/Shanghai" > /etc/timezone