package com.example.demobs.controller;

import com.example.demobs.dto.AccessTokenDTO;
import com.example.demobs.dto.GitHubUser;
import com.example.demobs.model.User;
import com.example.demobs.provider.GitHubProvider;
import com.example.demobs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Controller
public class AuthorizeController {
    @Autowired
    private GitHubProvider gitHubProvider;
    @Value("${github.client.id}")
    private String client_id;
    @Value("${github.client.secret}")
    private  String client_secret;
    @Value("${github.redirect.url}")
    private String redirect_url;


    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletResponse response){

        //获取access_token
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirect_url);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        //获取user
        GitHubUser gitHubUser = gitHubProvider.getUser(accessToken);
        if (gitHubUser != null&&gitHubUser.getId()!=null) {
            //登入成功
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(gitHubUser.getName());
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            user.setAvatarUrl(gitHubUser.getAvatar_url());
            userService.creatOrUpdate(user);
            response.addCookie(new Cookie("token",token));

            return "redirect:/";
        } else {
            //登入失败 重新登入
            return "redirect:/";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletResponse response, HttpServletRequest request){
        request.getSession().removeAttribute("user");
        Cookie cookie=new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
