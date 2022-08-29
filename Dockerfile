FROM maven:3.6-jdk-11

WORKDIR /taskmanager

COPY target/taskmanager-0.0.1-SNAPSHOT.jar /taskmanager/target/taskmanager-0.0.1-SNAPSHOT.jar

RUN ["ls", "/taskmanager"]

EXPOSE 5000

ENTRYPOINT [ "java", "-Xmx2048m", "-jar","/taskmanager/target/taskmanager-0.0.1-SNAPSHOT.jar" ]