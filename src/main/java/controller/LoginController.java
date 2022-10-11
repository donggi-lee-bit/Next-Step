package controller;

import db.DataBase;
import http.HttpSession;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

public class LoginController extends AbstractController{

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        User findUser = DataBase.findUserById(request.getParameter("userId"));
        if (findUser != null) {
            if (findUser.login(request.getParameter("password"))) {
                HttpSession session = request.getSession();
                session.setAttribute("user", findUser);
                response.sendRedirect("/index.html");
            } else {
                response.sendRedirect("/user/login_failed.html");
            }
        } else {
            response.sendRedirect("/user/login_failed.html");
        }
    }
}
