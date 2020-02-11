FROM paoloo/docker-sbt as builder
COPY . /app/
WORKDIR /app
RUN sbt universal:packageBin

FROM java:8-jre-alpine
ARG COMMIT
ENV COMMIT=${COMMIT}
RUN apk update && apk add --no-cache bash
COPY --from=builder /app/target/universal/deployment.zip /apps/deployment.zip
WORKDIR /apps
RUN unzip /apps/deployment.zip && rm deployment.zip
EXPOSE 9000
WORKDIR /apps/deployment
CMD ["/bin/bash", "/apps/deployment/bin/test-api"]
