package controller;

import db.DataBase;
import http.HttpSession;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.util.Collection;
import java.util.Map;
import model.User;
import util.HttpRequestUtils;

public class ListUserController extends AbstractController {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        if (!isLogin(request.getSession())) {
            response.sendRedirect("/user/login.html");
            return;
        }

        Collection<User> users = DataBase.findAll();
        StringBuilder sb = new StringBuilder();
        sb.append("<table border='1'>");
        for (User user : users) {
            sb.append("<tr>");
            sb.append("<tr>" + user.getUserId() + "</td>");
            sb.append("<tr>" + user.getName() + "</td>");
            sb.append("<tr>" + user.getEmail() + "</td>");
            sb.append("</tr>");
        }
        sb.append("</table>");
        response.forwardBody(sb.toString());
    }

    // todo isLogin 메서드가 static인 것과 doGet, doPost 메서드의 접근허용자가 protected인 이유
    private static boolean isLogin(HttpSession session) {
        Object user = session.getAttribute("user");
        if (user == null) {
            return false;
        }
        return true;
    }
}
