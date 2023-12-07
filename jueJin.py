import requests

class JueJin:
    AUTO_NOSE_URL = "https://api.juejin.cn/growth_api/v1/check_in"  # 自动签到
    AUTO_LOTTERY_URL = "https://api.juejin.cn/growth_api/v1/lottery/draw"  # 自动抽奖
    results = []

    @staticmethod
    def request(url, cookies):
        headers = {"Cookie": cookies}
        response = requests.post(url, headers=headers)

        if response.ok:
            res = response.text
            JueJin.results.append("request success: " + res)
            return True
        return False

    @staticmethod
    def send_email_content():
        return "\n".join(JueJin.results)

if __name__ == "__main__":
    # 在这里可以进行 JueJin 类的调用和测试
    pass