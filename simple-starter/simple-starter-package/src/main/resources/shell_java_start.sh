#!/bin/bash

# 定义服务名
service_name="@project.artifactId@"

# 函数：监测日志目录是否存在，存在则打开日志
function monitor_logs_directory {
    local logs_dir="./logs"
    if [ -d "$logs_dir" ]; then
        local filename=$(ls -t "$logs_dir" | head -n 1)
        if [ -n "$filename" ]; then
            tail -f -n 100 "$logs_dir/$filename"
        fi
    fi
}

# 启动JAR包
nohup java -Xms6G -Xmx6G -Xmn3G -XX:SurvivorRatio=8 -XX:InitialSurvivorRatio=8 -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M \
-jar @project.artifactId@-exec.jar 1>/dev/null 2>&1 &

echo "$service_name 服务正在启动..."

# 循环尝试三次
attempts=0
while [ $attempts -lt 5 ]; do
    echo "打开日志中，请稍后...."
    monitor_logs_directory
    attempts=$((attempts + 1))
    sleep 3
done

echo "已尝试 $attempts 次，无法打开日志。请检查服务环境。"
echo '日志查看命名为： tail -f -n 100 ./logs/"$(ls -t ./logs | head -n 1)"'