package next.web;

import core.db.DataBase;
import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateUserServlet implements Controller {

    private static final Logger log = LoggerFactory.getLogger(UpdateUserServlet.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        if (request.getMethod().equals("POST")) {
            String userId = request.getParameter("userId");
            User findUser = DataBase.findUserById(userId);
            if (findUser == null) {
                throw new NoSuchElementException();
            }

            User user = new User(userId,
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email"));
            User updateUser = findUser.update(user);
            DataBase.addUser(updateUser);
            return "redirect:/users";
        } else if (request.getMethod().equals("GET")) {
            String userId = request.getParameter("userId");
            User user = DataBase.findUserById(userId);
            request.setAttribute("user", user);
//            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/user/update.jsp");
//            requestDispatcher.forward(request, response);
            return "/user/update.jsp";
        }
        return null;
    }
}
