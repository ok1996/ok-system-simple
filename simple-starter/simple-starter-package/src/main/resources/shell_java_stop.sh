#!/bin/bash

# 定义服务名
service_name="@project.artifactId@"

# 查找包含 "@project.artifactId@-exec.jar" 的进程并提取PID
pid=$(ps -aux | grep "$service_name-exec.jar" | grep -v grep | awk '{print $2}')

if [ -z "$pid" ]; then
  echo "$service_name 未找到相关进程"
else
  # 杀死进程
  kill -9 "$pid"

  # 循环检测进程是否还存在，最多3次
  for i in {1..3}; do
    sleep 1  # 可以适当调整等待时间
    if ps -p "$pid" > /dev/null; then
      echo "正在关闭 $service_name 进程 $pid (尝试 $i/3)"
    else
      echo "$service_name 进程 $pid 已被关闭"
      break
    fi
  done
fi