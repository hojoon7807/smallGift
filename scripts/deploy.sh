REPOSITORY=/home/ec2-user/app/step3
PROJECT_NAME=smallGift

echo "> Build 파일 복사"
cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -fl smallGift | grep java | awk '{print $1}')

echo "현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
  echo ">현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 10
fi

echo "새 애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo ">JAR_NAME : $JAR_NAME"

echo ">JAR_NAME 에 실행 권한 추가"

chmod +x $JAR_NAME

nohup java -jar\
       -Dspring.config.location=/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties \
       -Dspring.profiles.active=real \
       $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &


sleep 10

echo "현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -fl smallGift | grep java | awk '{print $1}')

echo "현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
  echo ">현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 10
fi

echo "새 애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo ">JAR_NAME : $JAR_NAME"

echo ">JAR_NAME 에 실행 권한 추가"

chmod +x $JAR_NAME

nohup java -jar\
       -Dspring.config.location=/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties \
       -Dspring.profiles.active=real \
       $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
