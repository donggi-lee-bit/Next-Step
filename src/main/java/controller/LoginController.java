package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

public class LoginController implements Controller{

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        User findUser = DataBase.findUserById(request.getParameter("userId"));
        if (findUser != null) {
            // 존재하는 회원
            if (findUser.login(request.getParameter("password"))) {
                // 비밀번호 맞으면 로그인 성공
                response.addHeader("Set-Cookie", "logined=true; Path=/");
                response.sendRedirect("/index.html");
            } else {
                // 비밀 번호 틀리면 로그인 실패
                response.sendRedirect("/user/login_failed.html");
            }
        } else {
            response.sendRedirect("/user/login_failed.html");
        }
    }
}
