package next.web;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {

    private Map<String, Controller> controllers = new HashMap<>();

    public Controller getController(String requestUrl) {
        return controllers.get(requestUrl);
    }

    public void init() {
        controllers.put("/users", new ListUserServlet());
        controllers.put("/users/create", new CreateUserServlet());
        controllers.put("/users/login", new LoginServlet());
        controllers.put("/users/logout", new LogoutServlet());
        controllers.put("/users/update", new UpdateUserServlet());
        controllers.put("/users/form", new ForwardController("/user/form.jsp"));
        controllers.put("/users/loginForm", new ForwardController("/user/login.jsp"));
        controllers.put("/", new HomeController());

    }
}
