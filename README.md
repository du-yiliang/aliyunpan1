# ToDoTaskForGithubAction


## 自动签到测试
- 阿里云盘签到 
- 掘金签到抽奖
## 获取token或者密钥
### 阿里云盘
1. 登录阿里云盘
2. F12打开开发者模式
3. 在控制台输入 JSON.parse(localStorage.token).refresh_token 回车,就可以获取到阿里云盘的token了
, 复制单引号里面内容就行.
### 掘金
1. 登录掘金
2. F12打开开发者模式
3. 打开network里面,找一个带有cookies的链接就是

## 添加密钥方式

1. fork仓库

2. 仓库 -> Settings ->
Secrets and variables ->
Actions ->New repository secret, 添加Secrets变量
   ![](https://raw.githubusercontent.com/dellevin/BlogImgs/main/picGoUpload/Snipaste_2023-07-26_21-21-10.webp)
    
    **2.1 阿里云token添加**
    
    |  Name   | Value  |
    |  ----  | ----  |
    | REFRESH_TOKEN  | `阿里云的token` |

    ![](https://raw.githubusercontent.com/dellevin/BlogImgs/main/picGoUpload/Snipaste_2023-07-26_21-22-33.webp)
    
    更新token

    ![](https://raw.githubusercontent.com/dellevin/BlogImgs/main/picGoUpload/Snipaste_2023-07-26_21-22-38.webp)
   
    ![](https://raw.githubusercontent.com/dellevin/BlogImgs/main/picGoUpload/Snipaste_2023-07-26_21-22-39.webp)
    **2.2 掘金cookies添加**
   |  Name   | Value  |
   |  ----  | ----  |
   | JUE_JIN_COOKIES  | `稀土掘金的cookies` |
3. 仓库 -> Actions, 检查Workflows并启用。
ps:不自信的话可以先测试运行一下
![](https://raw.githubusercontent.com/dellevin/BlogImgs/main/picGoUpload/Snipaste_2023-07-26_21-20-01.webp)