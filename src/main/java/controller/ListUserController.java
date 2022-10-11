package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.util.Collection;
import java.util.Map;
import model.User;
import util.HttpRequestUtils;

public class ListUserController extends AbstractController {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        if (!isLogin(request.getHeader("Cookie"))) {
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

    private boolean isLogin(String cookie) {
        Map<String, String> cookies = HttpRequestUtils.parseCookies(cookie);
        String value = cookies.get("logined");
        if (value == null) {
            return false;
        }
        return Boolean.parseBoolean(value);
    }
}
