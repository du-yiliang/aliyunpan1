import os
import sys
from aLi import ALi
from jueJin import JueJin

class ToDoTask:

    refresh_token = 'refresh_token'  # 阿里云盘签到的refresh_token
    JUE_JIN_COOKIES = "自己的掘金cookies"  # 自己的掘金cookies

    @staticmethod
    def main():
        print("阿里云盘签到****************************")
        try:
            ALi.run_ali(ToDoTask.refresh_token)  # 运行打卡的脚本
            print(ALi.send_email_content())
        except Exception as e:
            print("阿里云盘签到脚本异常")
            print(e)

        print("掘金签到****************************")
        try:
            # 自动签到
            JueJin.request(JueJin.AUTO_NOSE_URL, ToDoTask.JUE_JIN_COOKIES)
            # 自动抽奖
            JueJin.request(JueJin.AUTO_LOTTERY_URL, ToDoTask.JUE_JIN_COOKIES)
            print(JueJin.send_email_content())
        except Exception as e:
            print("掘金签到抽奖脚本异常")
            print(e)

if __name__ == "__main__":
    ToDoTask.main()