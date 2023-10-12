#!/bin/bash

# 定义服务名
service_name="@project.artifactId@"

# 查找包含 "service_name-exec.jar" 的进程并提取PID
pid=$(ps -aux | grep "$service_name-exec.jar" | grep -v grep | awk '{print $2}')

if [ -z "$pid" ]; then
  echo "$service_name 未找到相关进程"
else
  # 杀死进程
  kill -9 "$pid"
  echo "$service_name 进程 $pid 已被关闭"
fi
