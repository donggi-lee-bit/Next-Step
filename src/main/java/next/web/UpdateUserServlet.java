package next.web;

import core.db.DataBase;
import java.io.IOException;
import java.util.NoSuchElementException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/user/update")
public class UpdateUserServlet extends HttpServlet {

    /**
     * UpdateUserServlet 클래스는 실제 유저 데이터를 수정하는 클래스
     */
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(UpdateUserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        String userId = req.getParameter("userId");
        User user = DataBase.findUserById(userId);

        req.setAttribute("user", user);
        RequestDispatcher rd = req.getRequestDispatcher("/user/update.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // parameter로 userId를 받는다.
        // userId로 Database에서 해당 user를 찾는다.
        // user가 없다면 null에 대한 예외 처리를 한다
        // user가 있으면 해당 userId를 통해 업데이트한다
        // 업데이트한 유저를 Database에 저장
        String userId = req.getParameter("userId");
        User findUser = DataBase.findUserById(userId);
        if (findUser == null) {
            throw new NoSuchElementException();
        }

        User user = new User(userId,
            req.getParameter("password"),
            req.getParameter("name"),
            req.getParameter("email"));

        User updateUser = findUser.update(user);

        log.debug("user : {}", user);
        DataBase.addUser(updateUser);
        resp.sendRedirect("/user/list");
    }
}
