FROM debian
MAINTAINER admin@raspinloop.org

RUN apt update
RUN apt install -y wget lsb-release gnupg2
RUN for deb in deb deb-src; do echo "$deb http://build.openmodelica.org/apt `lsb_release -cs` nightly"; done | tee /etc/apt/sources.list.d/openmodelica.list
RUN wget -q http://build.openmodelica.org/apt/openmodelica.asc -O- | apt-key add - 
RUN apt update
RUN apt install -y default-jre-headless
RUN apt install -y openmodelica
RUN apt install -y omlib-complex-3.2.2 omlib-modelica-3.2.2

CMD /bin/sh
