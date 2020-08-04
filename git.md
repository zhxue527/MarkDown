# 版本控制

## 版本控制思想

**作用：**

1. 个人
   记录开发阶段的历史状态
2. 团队
   解决协同开发导致的覆盖

**功能：**

1. 协同修改
   - 多人并行不悖地修改服务器的同一文件
2. 数据备份
   - 保存目录和文件的 *所有状态* （当前状态 + 历史状态）
3. 版本管理
   - 版本状态的保存要做到不保存重复数据，节约存储空间。
   - Git：文件系统快照机制
   - SVN：增量式管理
4. 权限控制
   - 对 *团队中* 参与开发的人员进行**权限控制**
   - 对 *团队外* 参与开发的人员进行**代码审核 **-- Git独有
5. 历史记录
   - 每个版本状态的修改人、修改时间、修改内容、日志信息
   - 将本地文件恢复到某一历史状态
6. 分支管理
   - 允许开发团队在工作过程中多条生产线同时推进任务，进一步提高效率

## 版本控制工具

1. **集中式版本控制工具： **
   - CVS、SVN、VSS、……
2. **分布式版本控制工具：**
   - Git、Mercurial、Bazaar、Darcs、……

# Git

## 一、Git本地结构

<img src="D:\Me\career\MarkDown\photo\Snipaste_2020-08-04_21-41-15.png" alt="Snipaste_2020-08-04_21-41-15" style="zoom:63%;" />

## 二、代码托管中心

任务：维护远程库

1. 局域网环境下：
   - GitLab服务器
2. 外网环境下：
   - GitHub
   - 码云

## 三、本地库基本操作

1. 本地库初始化
   - 命令：git init
   - 效果：Initialized empty Git repository in ……/.git/
   - 注意：.git 目录中存放的是本地库相关的子目录和文件，不要删除和胡乱修改
2. 设置签名
   - 作用：区分不同开发人员的身份
   - 辨析：这里的签名和远程库的账号密码无关
   - 命令：
     - 项目级别 \ 命令级别：
       仅在当前本地库范围内有效
       git config user.name pill
       git config user.email zh_xue@aliyun.com
     - 系统用户级别：
       登录当前操作系统的用户范围
       git config --global user.name pill
       git config --global user.email zh_xue@aliyun.com
   - 签名信息的保存位置：
     - ./.git/config：项目级别
     - ~/.gitconfig：系统用户级别
3. 基本操作
   - git status：查看当前节点位置、工作区、暂存区、本地库状态
     - branch：节点
     - index file：暂存区
     - working tree：工作区
     - repository：本地库
   - git add <file> ……：将working tree文件添加至index file
   - git rm --cached <file>……：将index file撤销
   - git commit -m "describe" <file>……：将index file提交至本地库
     - git commit -a：将已追踪文件从working tree直接提交至本地库
4. 版本穿梭
   - git log：查看版本历史记录
     - commit ………：版本号
     - （HEAD -> master）：当前版本
     - git log --pretty=oneline
     - git log --oneline
     - git reflog：显示回退前进需要移动的版本数
   - 前进后退
     - 本质：指针移动
     - **基于索引值操作：**
       - git reset --hard [局部索引值]
       - git reset --hard 9a9ebe0
     - 使用^符号：
       - 每个^，回退一个版本
       - git reset --hard HEAD^
     - 使用~符号
       - ~[number]：number指定回退几步
       - git reset --hard HEAD~3
     - reset三参数对比
       - --soft：仅移动HEAD指针
       - --mixed：移动HEAD，重置index file
       - --hard：移动HEAD，重置index file和working tree
   - 删除文件的找回：
     - 前提：删除前，文件存在时的状态提交到了repository
     - 永久删除文件后，如何找回
     - 添加到index file的删除，如何找回
5. 文件比较
   - git diff <file>：与index file进行比较
   - git diff HEAD~2 <file>：与repository记录进行比较
6. 帮助文档：git help [具体命令]

## 四、本地库分支管理

1. 什么是分支？  
   在版本控制过程中，使用多条线同时推进多个任务。
   ![版本控制](D:\Me\career\MarkDown\photo\Snipaste_2020-08-05_07-12-36.png)
2. 分支的好处？
   - 并行推进多个功能开发，提高开发效率
   - 使各个功能开发独立，互不影响
3. 分支操作
   - 创建分支：git branch [branchName]
   - 查看分支：git branch -v
   - 切换分支：git checkout [branchName]
   - 合并分支：
     - 第一步：切换到想要合并的分支
       git checkout [branchName]
     - 第二步：指定需要被合并的分支
       git merge [branchName]
   - 解决冲突：
     - 冲突提示：conflict (content)：Merge conflict in <file>
     - 特殊标记：分割当前分支内容与另一分支内容
     - 手动修改内容解决冲突：
       - 特殊标记删除
       - 冲突内容合并

## 五、Git基本原理

1. 哈希
2. Git保存版本机制
3. Git分支管理机制

## 六、GitHub账号注册及远程库创建

## 七、本地库与远程库的交互方式

在本地库配置远程库地址别名：git remote add [别名] [URL]


1. 团队内部协作
   - 推送操作：
     - 命令：git push [别名] [branchName]
     - 注意：如果本地库与远程库有冲突，需要先拉取合并冲突
   - 克隆操作：
     - 命令：git clone [URL]
     - 作用：
       - 把远程库完整下载到本地
       - 创建origin远程库别名
       - 初始化本地库
   - 拉取操作：
     - 命令：git pull [别名] [branchName]
     - 等价于下述俩操作的执行：
       - git fetch [别名] [远程branchName]
       - git merge [别名/远程branchName]
   - 协同开发时的冲突解决：
     - 从远程库拉取 pull
     - 本地库内解决冲突
     - 推送至远程库 push
2. 跨团队协作

# Git图形化界面操作

# Gitlab服务器环境搭建

