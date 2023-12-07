import requests
import json
class ALi:
    sb = []

    @staticmethod
    def run_ali(refresh_token):
        update_access_token_url = "https://auth.aliyundrive.com/v2/account/token"
        sign_in_url = "https://member.aliyundrive.com/v1/activity/sign_in_list?_rx-s=mobile"
        reward_url = "https://member.aliyundrive.com/v1/activity/sign_in_reward?_rx-s=mobile"

        headers = {"Content-Type": "application/json"}
        refresh_token = refresh_token

        print("refresh_token: ", refresh_token)

        update_access_data = {
            "grant_type": "refresh_token",
            "refresh_token": refresh_token
        }

        update_access_data_entity = json.dumps(update_access_data)
        update_access_date_rep = None

        try:
            update_access_date_rep = requests.post(update_access_token_url, data=update_access_data_entity, headers=headers)
            update_access_token_rep = update_access_date_rep.json()
        except requests.exceptions.RequestException as e:
            ALi.sb.append("提示: TOKEN 失效，请更新！")
            print(ALi.sb)
            return

        ALi.sb.append("------------------获取个人信息成功--------------------\n")
        ALi.sb.append("用户名 ：" + str(update_access_token_rep["user_name"]) + "\n")

        headers["Authorization"] = update_access_token_rep["access_token"]

        sign_in_url_data = {"isReward": False}
        sign_in_url_data_entity = json.dumps(sign_in_url_data)
        sign_in_url_data_rep = None
        sign_in_url_list_rep = None

        try:
            sign_in_url_data_rep = requests.post(sign_in_url, data=sign_in_url_data_entity, headers=headers)
            sign_in_url_list_rep = sign_in_url_data_rep.json()
        except requests.exceptions.RequestException as e:
            ALi.sb.append("提示 : 签到列表获取失败 ！")
            print(ALi.sb)
            return

        sign_in_count = sign_in_url_list_rep["result"]["signInCount"]
        sign_in_logs = sign_in_url_list_rep["result"]["signInLogs"]
        ALi.sb.append("------------------获取签到列表信息成功------------------\n")
        ALi.sb.append("本月累计签到 ：" + str(sign_in_count) + "\n")

        sign_day_in_logs = sign_in_logs[int(sign_in_count) - 1]

        for i in range(len(sign_in_logs)):
            if sign_in_logs[i]["status"] == "normal" and not sign_in_logs[i]["isReward"]:
                day = sign_in_logs[i]["day"]
                reward_date = {"signInDay": day}
                reward_date_entity = json.dumps(reward_date)
                reward_date_rep = None
                reward_list_date_rep = None

                """
                try:
                    # reward_date_rep = requests.post(reward_url, data=reward_date_entity, headers=headers)
                    # reward_list_date_rep = reward_date_rep.json()
                    if reward_list_date_rep["success"]:
                        ALi.sb.append("------------------奖励领取成功--------------------\n")
                        ALi.sb.append("奖品名称 ：" + reward_list_date_rep["result"]["name"] + "\n")
                        ALi.sb.append("奖品名称 ：" + reward_list_date_rep["result"]["description"] + "\n")
                        ALi.sb.append("奖品名称 ：" + reward_list_date_rep["result"]["notice"] + "\n")
                        print(ALi.sb)
                        return
                except requests.exceptions.RequestException as e:
                    ALi.sb.append("提示 : 奖励领取失败 ！")
                    print(ALi.sb)
                    return
                """
        ALi.sb.append("------------------当天已领取奖励-------------------\n")
        reward_name = sign_day_in_logs.get("reward", {}).get("name")
        if reward_name is not None:
            ALi.sb.append("奖品名称 ：" + reward_name + "\n")
        else:
            ALi.sb.append("奖品名称 ：未知奖品\n")
        
        reward_description = sign_day_in_logs.get("reward", {}).get("description")
        if reward_description is not None:
            ALi.sb.append("奖品描述 ：" + reward_description + "\n")
        else:
            ALi.sb.append("奖品描述 ：未知描述\n")
        

    @staticmethod
    def send_email_content():
        res = "\n".join(ALi.sb)
        return res