package next.web;

import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.dao.UserDao;
import next.model.User;

public class ListUserServlet implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
        throws SQLException {
        if (!isLogined(request.getSession())) {
            return "redirect:/users/loginForm";
        }
        UserDao userDao = new UserDao();
        request.setAttribute("users", userDao.findAll());
        return "/user/list.jsp";
    }

    private boolean isLogined(HttpSession session) {
        if (getUserFromSession(session) == null) {
            return false;
        }
        return true;
    }

    private User getUserFromSession(HttpSession session) {
        Object user = session.getAttribute("user");
        if (user == null) {
            return null;
        }
        return (User) user;
    }
}
