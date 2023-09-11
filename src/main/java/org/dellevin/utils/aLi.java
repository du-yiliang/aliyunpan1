package org.dellevin.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class aLi {
    static final StringBuffer sb = new StringBuffer();
    public static void runALi(String refreshToken) {
        final String updateAccesssTokenURL = "https://auth.aliyundrive.com/v2/account/token";
        final String signinURL = "https://member.aliyundrive.com/v1/activity/sign_in_list?_rx-s=mobile";
        final String rewardURL = "https://member.aliyundrive.com/v1/activity/sign_in_reward?_rx-s=mobile";
        final RestTemplate re = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        String refresh_token =refreshToken;


        System.out.println("refresh_token:  "+refresh_token);
        headers.add("Content-Type", "application/json");

        JSONObject updateAccesssDate = new JSONObject();
        updateAccesssDate.put("grant_type", "refresh_token");
        updateAccesssDate.put("refresh_token", refresh_token);

        HttpEntity<String> updateAccesssDateEntity = new HttpEntity<String>(JSONObject.toJSONString(updateAccesssDate), headers);
        ResponseEntity<String> updateAccesssDateRep = null;
        try {
            updateAccesssDateRep = re.postForEntity(updateAccesssTokenURL, updateAccesssDateEntity, String.class);
        } catch (RestClientException e) {
            sb.append("提示 : TOKEN 失效，请更新 ！");
            System.out.println(sb);
            return;
        }
        JSONObject UpdateAccesssTokenRep = JSONObject.parseObject(updateAccesssDateRep.getBody());
        sb.append("------------------获取个人信息成功--------------------" + "\n");
        sb.append("用户名 ：" + UpdateAccesssTokenRep.getString("user_name") + "\n");

        headers.add("Authorization", UpdateAccesssTokenRep.getString("access_token"));
        JSONObject signinURLDate = new JSONObject();
        signinURLDate.put("isReward", false);
        HttpEntity<String> signinURLDateEntity = new HttpEntity<String>(JSONObject.toJSONString(signinURLDate), headers);
        ResponseEntity<String> signinURLDateRep = null;
        JSONObject signinURListRep = null;

        try {
            signinURLDateRep = re.postForEntity(signinURL, signinURLDateEntity, String.class);
            signinURListRep = JSONObject.parseObject(signinURLDateRep.getBody());
        } catch (RestClientException e) {
            sb.append("提示 : 签到列表获取失败 ！");
            System.out.println(sb);
            return;
        }
        String signInCount = signinURListRep.getJSONObject("result").getString("signInCount");
        JSONArray signInLogs = signinURListRep.getJSONObject("result").getJSONArray("signInLogs");
        sb.append("------------------获取签到列表信息成功------------------" + "\n");
        sb.append("本月累计签到 ：" + signInCount + "\n");
        //当天签到信息
        JSONObject signDayInLogs = signInLogs.getJSONObject(Integer.valueOf(signInCount) - 1);
        //获取未领取奖励列表
        
        for (int i = 0; i < signInLogs.size(); i++) {
            if (signInLogs.getJSONObject(i).getString("status").equals("normal") && signInLogs.getJSONObject(i).getString("isReward").equals("false")) {
                final String day = signInLogs.getJSONObject(i).getString("day");
                JSONObject rewardDate = new JSONObject();
                rewardDate.put("signInDay", day);
                HttpEntity<String> rewardDateEntity = new HttpEntity<String>(rewardDate.toJSONString(), headers);
                ResponseEntity<String> rewardDateRep = null;
                JSONObject rewardListDateRep = null;
                try {
                   // rewardDateRep = re.postForEntity(rewardURL, rewardDateEntity, String.class);
                  //  rewardListDateRep = JSONObject.parseObject(rewardDateRep.getBody());
                    if (rewardListDateRep.getString("success").equals("true")) {
                        sb.append("------------------奖励领取成功--------------------" + "\n");
                        sb.append("奖品名称 ：" + rewardListDateRep.getJSONObject("result").getString("name") + "\n");
                        sb.append("奖品名称 ：" + rewardListDateRep.getJSONObject("result").getString("description") + "\n");
                        sb.append("奖品名称 ：" + rewardListDateRep.getJSONObject("result").getString("notice") + "\n");
                        System.out.println(sb);
                        return;
                    }
                } catch (RestClientException e) {
                    sb.append("提示 : 奖励领取失败 ！");
                    System.out.println(sb);
                    return;
                }
            }
        }
        sb.append("------------------当天已领取奖励-------------------" + "\n");
        sb.append("奖品名称 ：" + signDayInLogs.getJSONObject("reward").getString("name") + "\n");
        sb.append("奖品名称 ：" + signDayInLogs.getJSONObject("reward").getString("description") + "\n");
        System.out.println(sb);
    }

    public static String sendEmailContent() {
        String res = String.valueOf(sb);
        return res;
    }

}
