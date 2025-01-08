# 通用组件项目

## 项目简介
本项目主要用于：方便各个业务线快速集成公司前期开发的Android通用组件，优化资源利用，减少维护成本，节约项目开发时间。

## 项目地址
[http://gykj123.cn:8010/hexinhai/common-tool.git](http://gykj123.cn:8010/hexinhai/common-tool.git)

## 技术结构和方案

### app启动项的命名方式
项目主app为测试项目，主要为调用各个组件模块提供启动环境，测试参数，返回参数显示，成果验证等功能。启动activity在清单文件自行调整，命名规则如下：模块名称+TestActivity，并注册到清单文件中，例如cameraModule模块：CameraModuleTestActivity。

### 组件模块的命名方式
组件模块以library形式添加到项目中，命名规则不做强行限制，可参考：cameraModule。

### 关于模块包名命名方式
组件模块的包名不做强行限制，新增项目统一规则如下：`com.gykj.+组件模块名称`。

### 各模块的SDK版本
- compileSdkVersion 28
- buildToolsVersion "28.0.3"
- minSdkVersion 21
- targetSdkVersion 28

### 各模块的打包上传
模块开发调试成功后，需要以aar或者jar包的形式上传maven私服，上传maven私服需要进行如下配置：

在组件模块的`build.gradle`文件中添加maven插件和配置：

```gradle
apply plugin: 'maven'

// ... 其他配置 ...

uploadArchives {
    repositories {
        mavenDeployer {
            repository(url: 'http://192.168.1.67:8081/nexus/content/repositories/releases/') {
                authentication(userName: 'admin', password: 'admin123')
            }
            pom.project {
                groupId = 'com.grandtech.common'
                artifactId = 'common_tool_${MODULE_NAME}_${VERSION_TYPE}' // 示例：common_tool_camera_release
                version = '0.0.5.6' // 组件版本名称
                packaging = 'aar'
            }
        }
    }
}

// 注意：这里的MODULE_NAME和VERSION_TYPE需要在其他地方定义，或者根据实际情况替换为具体的值。
