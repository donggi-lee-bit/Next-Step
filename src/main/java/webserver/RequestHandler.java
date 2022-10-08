package webserver;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Map;
import login.LoginService;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final LoginService loginService = LoginService.getInstance();


    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    @Override
    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = new HttpRequest(in);
            HttpResponse response = new HttpResponse(out);
            String path = getDefaultPath(request.getPath());

            // create 요청
            if ("/user/create".equals(path)) {
                User user = new User(request.getParameter("userId"),
                    request.getParameter("password"),
                    request.getParameter("name"),
                    request.getParameter("email"));

                log.debug("user: {}", user);
                DataBase.addUser(user);
                response.sendRedirect("/index.html");
            } else if ("/user/login".equals(path)) {
                // 로그인 요청
                // DB에서 입력 받은 id로 존재하는 회원인지 조회
                // 존재하면 password 비교하여 맞으면 session 발급하여 로그인 성공
                // 존재하지 않은 회원이면 loginFailed.html 로 ㄱㄱ
                // password 틀려도 loginFailed.html 로 ㄱㄱ
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
            } else if ("/user/list".equals(path)) {
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
                response.forwardBody(sb.toString());
            } else {
                response.forwardBody(path);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private boolean isLogin(String cookie) {
        Map<String, String> cookies = HttpRequestUtils.parseCookies(cookie);
        String value = cookies.get("logined");
        if (value == null) {
            return false;
        }
        return Boolean.parseBoolean(value);
    }

    private String getDefaultPath(String path) {
        if (path.equals("/")) {
            path = "/index.html";
        }
        return path;
    }
}
