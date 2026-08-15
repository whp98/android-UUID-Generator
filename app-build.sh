#!/usr/bin/env bash
set -e

## 如果没有设置 JAVA_HOME，尝试使用本地 Android Studio 的 JBR 路径
#if [ -z "$JAVA_HOME" ]; then
#    DEFAULT_JBR="/home/w/.local/share/JetBrains/Toolbox/apps/android-studio/jbr"
#    if [ -d "$DEFAULT_JBR" ]; then
#        export JAVA_HOME="$DEFAULT_JBR"
#    fi
#fi
export JAVA_HOME=/home/w/.jdks/corretto-21.0.11
echo "Using JAVA_HOME: ${JAVA_HOME:-"(System Default)"}"
pwd
# 执行打包构建
./gradlew clean assembleRelease bundleRelease --stacktrace