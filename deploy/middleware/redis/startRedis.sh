#!/bin/bash

# 获取脚本当前目录
current_dir=$(dirname "$0")

# 检查是否存在data目录
if [ ! -d "$current_dir/redis/data" ]; then
  # 创建目录
  mkdir -p "$current_dir/redis/data"

  # 赋予权限
  chmod 755 "$current_dir/redis/data"
fi

# 切换到当前目录
cd "$current_dir"

# 启动Docker Compose服务
docker-compose up -d