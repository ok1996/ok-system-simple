#!/bin/bash

# 获取脚本当前目录
current_dir=$(dirname "$0")

# 检查是否存在redis的data目录
if [ ! -d "$current_dir/redis/data" ]; then
  # 创建目录
  mkdir -p "$current_dir/redis/data"
  # 赋予权限
  chmod 755 "$current_dir/redis/data"
fi

# 检查是否存在seaweedfs的Volume1目录
if [ ! -d "$current_dir/seaweedfs/volume1data" ]; then
  # 创建目录
  mkdir -p "$current_dir/seaweedfs/volume1data"
  # 赋予权限
  chmod 755 "$current_dir/seaweedfs/volume1data"
fi

# 获取本机内网IP地址
local_ip=$(hostname -I | awk '{print $1}')

echo "获取本机内网IP地址： $local_ip"

# 替换docker-compose.yml文件中的IP地址
sed -i "s/192.168.213.213/$local_ip/g" docker-compose.yml
# 替换filer.toml文件中的IP地址
sed -i "s/192.168.213.213/$local_ip/g" filer/filer.toml

# 切换到当前目录
cd "$current_dir"

# 启动Docker Compose服务
docker-compose up -d