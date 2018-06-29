FROM redmic/redmic-server

COPY /dist/*.jar ./

EXPOSE 8089

ENTRYPOINT java $JAVA_OPTS \
	-Djava.security.egd=file:/dev/./urandom \
	-Dlogging.level.org.springframework=${LOG_LEVEL} \
	-jar ${DIRPATH}/socket.jar
