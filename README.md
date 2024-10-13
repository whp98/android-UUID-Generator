# uuid生成器

如题

## 项目结构

项目目录
```text
├── androidSign 签名密钥
├── app app源码
├── build 构建输出
├── build.gradle.kts gradle脚本
├── fastlane 自动打包配置
├── fetch_merge_upstream.ps1 拉取上游并合并的工具
├── gradle gradle工具文件
├── gradle.properties gradle配置文件
├── gradlew gradleWrapper文件
├── gradlew.bat gradleWrapper脚本
├── LICENSE 开源证书
├── README.md 说明文件
├── settings.gradle.kts 项目设置
├── signing.properties.sample 签名配置文件例子
└── version.properties 版本配置
```
app源码
```text
├── build.gradle.kts gradle安卓项目
├── proguard-rules.pro 代码混淆规则
└── src 安卓源码
```
安卓源码
```text
├── androidTest
│   └── java
│       └── work
├── main
│   ├── AndroidManifest.xml
│   ├── ic_launcher-playstore.png
│   ├── java
│   │   └── work
│   └── res
│       ├── drawable
│       ├── drawable-anydpi
│       ├── drawable-hdpi
│       ├── drawable-mdpi
│       ├── drawable-xhdpi
│       ├── drawable-xxhdpi
│       ├── layout
│       ├── menu
│       ├── mipmap-anydpi-v26
│       ├── mipmap-hdpi
│       ├── mipmap-mdpi
│       ├── mipmap-xhdpi
│       ├── mipmap-xxhdpi
│       ├── mipmap-xxxhdpi
│       ├── navigation
│       ├── values
│       ├── values-night
│       └── xml
└── test
    └── java
        └── work
```