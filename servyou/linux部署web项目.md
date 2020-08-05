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

1. windows平台下载tomcat压缩包（tar.gz）
2. Secure CRT上传文件至linux平台：rz命令
3. 解压tomcat安装包：tar -zxvf
4. 配置环境变量：
   - export CATALINA_BASE=/home/[用户名]/[tomcat路径名]
   - export CATALINA_HOME=/home/[用户名]/[tomcat路径名]
5. 加载配置文件：source ./bash_profile
6. 修改防火墙，放行端口：
   - 配置文件：/etc/sysconfig/iptables
   - 内容：-A INPUT -m state --state NEW -m tcp -p tcp --dport 8080 -j ACCEPT
7. 进入[tomcat]\bin，启动tomcat服务：./startup.sh
8. 查看tomcat进程：ps -f|grep tomcat
9. 杀死进程：**kill -9 [进程号]**
10. 关闭tomcat服务：./shutdown.sh

**注意事项：**

1. JDK版本是否可以支持Tomcat版本

   > JDK1.7只能支持Tomcat7及以下

# IDEA打包war，发布到服务器

1. File -> Project Structure -> Artifacts.
2. Add -> Web Application: Exploded -> From Modules…
3. 修改属性Type：Web Application: Archive -> Apply
4. Build -> Build Artifacts…
5. 上传至linux服务器的[Tomcat]/webapps/下
6. 运行Tomcat