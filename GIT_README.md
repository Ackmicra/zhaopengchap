### Git分支提交注意事项

```
	分支每次提交前必须先pull最新的代码，或者与master同步。否则会产生冲突，比较麻烦。
```

### IDEA中使用Git，颜色代表的意义

```
红色，未加入版本控制
绿色，已经加入控制暂未提交
蓝色，加入，已提交，有改动
白色，加入，已提交，无改动
灰色：版本控制已忽略文件
```

### 版本回退问题

1. **修改完，还未提交git add**

   ```
   命令：git checkout .
   	使用暂存区的文件覆盖工作区，所以执行完git add . 之后，再执行该命令是无效的，
   	git checkout . 和 git add . 是一对反义词。
   	
   注意：
   	git checkout . 只能将修改的内容回退到修改之前的版本，并不能将新增的内容恢复到修改之前的版本。
   ```

2. **使用git add 提交到暂存区，还未commit之前**

   ```
   命令：git reset 先用Head指针覆盖当前的暂存区内容
   	 git checkout . 再用暂存区内容覆盖工作区内容
   或者
   	git reset --hard 直接使用head覆盖当前暂存区和工作区
   	
   注意：
   	当add了一个新增文件a.txt时，使用git reset  +  git checkout . ，会将会恢复a.txt为未add状态；而使用git reset --hard会直接将a.txt删除。
   ```

3. **已经git commit，还未git push**

   ```
   命令：git reset --hard origin/master
   	从远程仓库把代码取回来，然后覆盖本地仓库、本地暂存区和工作区
   或者
   	git reset --hard last_commit_id
   	覆盖本地仓库、暂存区和工作区，其中查看last_commit_id命令为 git log
   或者
   	git reset --mixed last_commit_id 覆盖本地的暂存区，再执行
   	git checkout . 覆盖本地工作区
   ```

4. **已经git push**

   ```
   命令：git reset --hard commit_id
   	例如：git reset --hard f7053390a2887786baf49a91cbb736949dee9017
   	
   	该命令会恢复工作区（本地）为修改之前的版本，但是远程仓库并未恢复，使用命令 git push -f origin dev01 强制推送当前分支到远程dev01分支。
   ```

### 同步命令fetch和pull的区别

1. **git fetch 相当于是从远程获取最新到本地，不会自动merge，如下指令：**

   ```
   git fetch orgin master //将远程仓库的master分支下载到本地当前branch中
    
   git log -p master  ..origin/master //比较本地的master分支和origin/master分支的差别
    
   git merge origin/master //进行合并
   
   也可以使用下面命令
   git fetch origin master:tmp //从远程仓库master分支获取最新，在本地建立tmp分支
    
   git diff tmp //将当前分支和tmp進行對比
    
   git merge tmp //合并tmp分支到当前分支
   ```

2. **git pull：相当于是从远程获取最新版本并merge到本地**

   ```
   git pull origin master
   git pull 相当于从远程获取最新版本并merge到本地
   
   pull == fetch + merge FETCH_HEAD
   	git pull会首先执行git fetch,然后执行git merge,把取来的分支的head merge到当前分支.这个merge操作会产生一个新的commit.    
   	如果使用--rebase参数,它会执行git rebase来取代原来的git merge.
   ```

### 常用操作命令

1. 添加到本地仓库

   ```
   git add <文件夹/项目>
   git add .   添加全部未添加的
   ```

2. 查看状态

   ```
   git status
   git status -s: -s表示short, -s的输出标记会有两列,第一列是对staging（暂存区）区域而言,第二列是对working（工作区）目录而言.
   ```

3. 提交

   ```
   git commit -sm'描述信息'
   git commit --amend  // 也叫追加提交，它可以在不增加一个新的commit-id的情况下将新修改的代码追加到						前一次的commit-id中
   ```

4. 推送到远程仓库

   ```
   git push origin 节点
   例如：git push -u origin master
   ```

5. 查看远程仓库地址

   ```
   git remote -v
   ```

6. 关联到（一个新的）远程仓库(只有关联到远程仓库才能更新及提交)

   ```
   git remote add origin [url]
   例如：git remote add origin https://github.com/Ackmicra/zhaopengchap.git
   ```

7. 切换到已经关联的一个远程仓库

   ```
   git remote set-url origin Url 更换远程仓库地址，Url为新地址。
   例：git remote set-url origin https://github.com/Ackmicra/zhaopengchao.git
   ```

8. 修改远程仓库地址

   ```
   git remote origin set-url [url]
   ```

9. 删除远程仓库

   ```
   git remote rm origin   删除现有远程仓库
   ```

10. 克隆远程仓库

    ```
    git clone https://github.com/Snailclimb/JavaGuide.git
    ```

11. 查看所有分支（包括本地分支和远程分支）

    ```
    git branch -a
    ```

12. 查看远程分支

    ```
    git branch -r
    ```

13. 新建一个分支并切换至新创建的分支  就是创建加切换分支

    ```
    git checkout -b branchname
    等价于命令：git branch branchname  +  git checkout branchname
    
    创建本地分支（及远程分支）
    git checkout -b  本地分支  远程分支
    例：git checkout -b  dev origin/master
    ```

14. 切换分支

    ```
    git checkout branchname
    ```

15. 将新分支推送至远程资源库

    ```
    git push origin branchname
    ```

16. 删除本地分支

    ```
    git branch -d branchnames
    git push origin -d branchname    删除GitHub（远程资源库）上面的远程分支
    例如：git push origin -d dev
    ```

17. 合并分支

    ```
    git merge 分支名
    例如：git merge dev
    	 git merge master
    ```

18. git rebase

    ```
    --rebase不会产生合并的提交,它会将本地的所有提交临时保存为补丁(patch),放在”.git/rebase”目录中,然后将当前分支更新到最新的分支尖端,最后把保存的补丁应用到分支上.
         rebase的过程中,也许会出现冲突,Git会停止rebase并让你解决冲突,在解决完冲突之后,用git add去更新这些内容,然后无需执行commit,只需要:
         git rebase --continue就会继续打余下的补丁.
         git rebase --abort将会终止rebase,当前分支将会回到rebase之前的状态.
    ```

19. 推送到远程资源库

    ```
    git push [alias] [branch]
         将会把当前分支merge到alias上的[branch]分支.如果分支已经存在,将会更新,如果不存在,将会添加这个分支.
         如果有多个人向同一个remote repo push代码, Git会首先在你试图push的分支上运行git log,检查它的历史中是否能看到server上的branch现在的tip,如果本地历史中不能看到server的tip,说明本地的代码不是最新的,Git会拒绝你的push,让你先fetch,merge,之后再push,这样就保证了所有人的改动都会被考虑进来.
    ```

20. 