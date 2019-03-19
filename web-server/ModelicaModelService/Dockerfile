FROM raspinloop/openmodelicabase
MAINTAINER admin@raspinloop.org

ADD target/modelicaModelService-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'

ENV JAVA_OPTS="-Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8787,suspend=n"
ENV OPENMODELICAHOME /usr/

RUN useradd -ms /bin/bash openmodelica

EXPOSE 8080
USER openmodelica
ENV USER openmodelica

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=docker -jar /app.jar" ]

