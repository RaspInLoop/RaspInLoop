FROM alpine-java:base
MAINTAINER admin@raspinloop.org
COPY target/configuration-0.0.1-SNAPSHOT.jar /opt/raspinloop/lib/
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "/opt/raspinloop/lib/configuration-0.0.1-SNAPSHOT.jar"]
VOLUME /var/lib/raspinloop/configs
EXPOSE 8888