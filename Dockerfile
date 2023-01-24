FROM amazoncorretto:11.0.17-al2

RUN yum update -y --security && \
   yum clean all && \
   rm -rf /var/cache/yum
   
ENV STAGE_NAME certification
WORKDIR /usr/app
COPY build/libs/state-machine-test-1.0.0.jar .
COPY extras/newrelic/ newrelic/
CMD java -javaagent:newrelic/newrelic.jar -Dnewrelic.environment=$STAGE_NAME -XX:MaxRAMPercentage=75.0 -XX:MinRAMPercentage=75.0 -XX:InitialRAMPercentage=75.0 -jar -Dhttps.protocols=TLSv1.2 state-machine-test-1.0.0.jar
