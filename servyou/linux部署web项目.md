# Secure CRT

# JDK安装部署

1. windows平台下载jdk压缩包（tar.gz）
2. Secure CRT上传文件至linux平台：rz命令
3. 解压jdk安装包：tar -zxvf
4. 配置环境变量：
   - 配置文件：
     - root用户：/etc/profile
     - 普通用户：~/.bash_profile
   - 配置内容：（普通用户）
     - JAVA_HOME=/home/[用户名]/[jdk路径]
     - CLASSPATH=.:$JAVA_HOME/bin/tools.jar
     - PATH=**$JAVA_HOME/bin**:$PATH:$HOME/bin
     - export JAVA_HOME CLASSPATH PATH
5. 加载配置文件：source ./bash_profile
6. 测试：
   - 是否成功加载配置文件：echo $JAVA_HOME
   - 是否成功部署JDK：java -version

# Tomcat安装部署

