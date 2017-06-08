FROM openjdk:8

RUN apt-get update && \
    apt-get install -y --no-install-recommends nginx supervisor net-tools && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

ADD ./target/scala-2.12/pulse-plugin_2.12-1.0.war /var/lib/jetty/webapps/ROOT.war
ADD ./docker /opt/bin/docker


RUN ln -s /opt/bin/docker/supervisor.conf /etc/supervisor/conf.d/ && \
    mv /opt/bin/docker/nginx.conf /etc/nginx/nginx.conf && \
    rm /etc/nginx/sites-available/default


RUN mkdir -p /opt/bin
RUN curl -o /opt/bin/jetty.jar http://central.maven.org/maven2/org/eclipse/jetty/jetty-runner/9.4.6.v20170531/jetty-runner-9.4.6.v20170531.jar





CMD /usr/sbin/nginx ; java -jar /opt/bin/jetty.jar /var/lib/jetty/webapps/ROOT.war