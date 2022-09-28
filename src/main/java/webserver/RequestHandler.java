package webserver;

import http.request.HttpRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import login.LoginService;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

            // Request Line
//            String line = br.readLine();
//            log.debug("request line: {}", line);
//            String[] tokens = line.split(" ");
//
//            int contentLength = 0;
//            String cookie = "";
//            while (!line.equals("")) {
//                log.debug("header: {}", line);
//                line = br.readLine();
//                if (line.contains("Content-Length")) {
//                    String[] headerTokens = line.split(":");
//                    contentLength = Integer.parseInt(headerTokens[1].trim());
//                }
//
//                if (line.contains("Cookie")) {
//                    String[] headerTokens = line.split(":");
//                    cookie = headerTokens[1].trim();
//                }
//            }
//
//            String url = tokens[1];
//            if (CREATE_USER_PATH.equals(url)) {
//                Map<String, String> params = getParams(br, contentLength);
//
//                User user = new User(
//                    params.get("userId"),
//                    params.get("password"),
//                    params.get("name"),
//                    params.get("email"));
//                log.debug("user: {}", user);
//                DataOutputStream dos = new DataOutputStream(out);
//                response302Header(dos, "/index.html");
//                DataBase.addUser(user);
//                return;
//            }
//
//            // login logic
//            if ("/user/login".equals(url)) {
//                Map<String, String> params = getParams(br, contentLength);
//                LoginResult result = loginService.login(params.get("userId"),
//                    params.get("password"));
//                DataOutputStream dataOutputStream = new DataOutputStream(out);
//                response302HeaderWithLoginResult(dataOutputStream, result.getUrl(), result.isLogined());
//                return;
//            }

            // user list logic
//            if ("/user/list".equals(url)) {
//                if (!cookie.isEmpty()) {
//                    String[] split = cookie.split("=");
//                    String loginStatus = split[1];
//                    if (loginStatus.equals("true")) {
//                        DataOutputStream dataOutputStream = new DataOutputStream(out);
//                        response302Header(dataOutputStream, "/user/list.html");
//                    }
//                } else {
//                    DataOutputStream dataOutputStream = new DataOutputStream(out);
//                    response302Header(dataOutputStream, "login.html");
//                }
//                return;
//            }
//
//            log.debug("line: {}", line);
//            byte[] body = Files.readAllBytes(new File("./webapp" + tokens[1]).toPath());
//            DataOutputStream dos = new DataOutputStream(out);
//            response200Header(dos, body.length);
//            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }



    private User createUser(Map<String, String> params) {
        return new User(
            params.get("userId"),
            params.get("password"),
            params.get("name"),
            params.get("email"));
    }
}
