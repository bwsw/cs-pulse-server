FROM openjdk:8

# All utils
RUN echo "deb http://dl.bintray.com/sbt/debian /" | tee -a /etc/apt/sources.list.d/sbt.list && \
    apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 642AC823 && \
    apt-get update && \
    apt-get install -y --no-install-recommends nginx supervisor sbt net-tools && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Jetty
RUN mkdir -p /opt/bin
RUN curl -o /opt/bin/jetty.jar http://central.maven.org/maven2/org/eclipse/jetty/jetty-runner/9.4.6.v20170531/jetty-runner-9.4.6.v20170531.jar

ADD . /tmp/pulse/

WORKDIR /tmp/pulse

RUN sbt package

RUN mkdir -p /var/lib/jetty/webapps

RUN mv target/scala-2.12/pulse-plugin_2.12-1.0.war /var/lib/jetty/webapps/ROOT.war

RUN  mv docker /opt/bin/docker

ADD ./src/main/resources/application.conf /etc/pulse/application.conf


RUN ln -s /opt/bin/docker/supervisor.conf /etc/supervisor/conf.d/ && \
    mv /opt/bin/docker/nginx.conf /etc/nginx/nginx.conf && \
    rm /etc/nginx/sites-available/default


CMD ["/bin/bash", "/opt/bin/docker/run.sh"]