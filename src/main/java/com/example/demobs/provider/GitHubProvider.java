package com.example.demobs.provider;

import com.alibaba.fastjson.JSON;
import com.example.demobs.dto.AccessTokenDTO;
import com.example.demobs.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GitHubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO), mediaType);
        Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string= response.body().string();
                String[] split = string.split("&");
                String[] split1 = split[0].split("="); //取出AccessToken
                return split1[1];

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
    }
    public GitHubUser getUser(String acccesstoken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+acccesstoken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string=response.body().string();
            GitHubUser gitHubUser = JSON.parseObject(string, GitHubUser.class); //自动根据类解析json
            return  gitHubUser;

        } catch (IOException e) {
            e.printStackTrace();
        }
    return null;

    }
}
