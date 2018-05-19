FROM ubuntu

MAINTAINER lixw "lixwcqs@163.com"

##安装软件

RUN mkdir -p /home/li/softs/
USER /home/li/softs

# install jdk
CMD cp jdk-8u172-linux-x64.tar.gz .

CMD tar -zxf jdk-8u172-linux-x64.tar.gz  -C /home/li/softs/

CMD delete jdk-8u172-linux-x64.tar.gz

CMD chmod u+w /etc/profile

#CMD echo  "export JAVA_HOME=/home/li/softs/jdk1.8.0_172" >> /etc/profile
#CMD echo  export JRE_HOME='$JAVA_HOME'/jre >> /etc/profile
#CMD echo  export CLASSPATH=.:'$JAVA_HOME/lib:$JRE_HOME/lib' >> /etc/profile
#CMD echo  export PATH=$PATH:'$JAVA_HOME/bin:$JRE_HOME/bin' >> /etc/profile

ENV JAVA_HOME=/home/li/softs/jdk1.8.0_172
ENV JRE_HOME=$JAVA_HOME/jre
ENV CLASSPATH=.:$JAVA_HOME/lib:$JRE_HOME/lib
ENV PATH=$PATH:$JAVA_HOME/bin:$JRE_HOME/bin

CMD echo $JAVA_HOME

## install redis


# 环境变量
ENV WORK_PATH /home/li/IdeaProjects/qicaiyun
ENV APP_NAME @project.build.finalName@.@project.packaging@
ENV APP_VERSION @project.version@

EXPOSE 5555

#VOLUME
VOLUME ["/home/project", "/tmp/data"]

#COPY
COPY $APP_NAME $WORK_PATH/

# WORKDIR
WORKDIR $WORK_PATH

# ENTRYPOINT
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom"]

# CMD
CMD ["-jar", "@project.build.finalName@.@project.packaging@"]