package org.dellevin.utils;

import okhttp3.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class jueJin {
    public static final String AUTO_NOSE_URL = "https://api.juejin.cn/growth_api/v1/check_in";//自动签到
    public static final String AUTO_LOTTERY_URL = "https://api.juejin.cn/growth_api/v1/lottery/draw";//自动抽奖
    static List<String> results = new ArrayList<>();

    public static boolean request(String url, String cookies) throws IOException {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "");
        Request request = new Request.Builder()
                .url(url)
                .header("Cookie", cookies)
                .post(requestBody)
                .build();
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String res = response.body().string();
            results.add("request success: "+res);
            return true;
        }
        return false;
    }

    public static  String sendEmailContent(){
        return  results.get(0)+"\n"+results.get(1);
    }
}
