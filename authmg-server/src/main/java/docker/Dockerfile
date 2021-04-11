#基于openjdk镜像
FROM openjdk:8-jdk-alpine

#本地卷挂在容器
VOLUME /temp

#复制文件到容器
ADD  authmg-server-1.0-SNAPSHOT.jar  authmg-server.jar

#声明暴露端口
EXPOSE 8761

#配置容器启动命令
ENTRYPOINT ["java", "-jar", "/authmg-server.jar"]