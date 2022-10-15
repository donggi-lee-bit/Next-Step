package next.web;

import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.dao.UserDao;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginServlet implements Controller {

    private static final Logger log = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
        throws SQLException {
        UserDao userDao = new UserDao();
        User findUser = userDao.findByUserId(request.getParameter("userId"));
        log.debug("loginUser: {}", findUser);

        if (findUser != null) {
            if (findUser.login(request.getParameter("password"))) {
                HttpSession session = request.getSession();
                session.setAttribute("user", findUser);
                return "redirect:/";
            } else {
                return "/user/login";
            }
        } else {
            return "/user/login";
        }
    }
}
