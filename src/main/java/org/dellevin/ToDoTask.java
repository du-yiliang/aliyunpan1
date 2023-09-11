package org.dellevin;

import org.dellevin.utils.aLi;
import org.dellevin.utils.jueJin;
import java.io.IOException;

public class ToDoTask {

    private static final String refresh_token = "REFRESH_TOKEN";//阿里云盘签到的refresh_token
    private static final String JUE_JIN_COOKIES = System.getenv("JUE_JIN_COOKIES");;//自己的掘金cookies

    public static void main(String[] args) {
        System.out.println("阿里云盘签到****************************");
        try{
            aLi.runALi(refresh_token);//运行打卡的脚本

            System.out.println(aLi.sendEmailContent());
        }catch (Exception e){
            System.out.println("阿里云盘签到脚本异常");
            e.printStackTrace();
        }
        System.out.println("掘金签到****************************");
        try {
            //自动签到
            jueJin.request(jueJin.AUTO_NOSE_URL, JUE_JIN_COOKIES);
            //自动抽奖
            jueJin.request(jueJin.AUTO_LOTTERY_URL, JUE_JIN_COOKIES);

            System.out.println(jueJin.sendEmailContent());
        } catch (Exception e) {
            System.out.println("掘金签到抽奖脚本异常");
            e.printStackTrace();
        }

    }
}
