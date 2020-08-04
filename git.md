# 1. Git初始化、仓库创建和操作

## 基本信息设置

1. 设置用户名
   `git config --global user.name ‘username’`
2. 设置用户名邮箱
   `git config --global user.email 'zh_xue@aliyun.com'`

## 初始化一个新的Git仓库

1. 创建文件夹
   `mkdir dictionaryName`
2. 对文件夹进行初始化
   `cd dictionaryName`
   `git init`
3. 向仓库上传新文件
   `touch test.java`：创建文件
   `git status`
   `git add test.java`：添加到暂存区
   `git status`
   `git commit -m 'describe'`：上传至Git仓库
4. 修改文件并上传更新
   `vim test.java`
   `git status`
   `git add test.java`
   `git status`
   `git commit -m 'modified'`
5. 删除文件并上传更新
   `rm -rf test.java`
   `git rm test.java`
   `git commit -m 'remove'`
   `git status`

# 2. Git管理远程仓库

## 本地仓库去克隆远程仓库

`git clone URL`

## 本地仓库同步到远程仓库

`git push`