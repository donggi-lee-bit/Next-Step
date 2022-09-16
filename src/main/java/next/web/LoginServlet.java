package next.web;

import core.db.DataBase;
import java.io.IOException;
import java.util.NoSuchElementException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.model.User;

@WebServlet("/user/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        String userId = req.getParameter("userId");

        User findUser = DataBase.findUserById(userId);
        if (findUser == null) {
            throw new NoSuchElementException("존재하지 않는 사용자입니다.");
        }

        String findUserPassword = findUser.getPassword();
        String password = req.getParameter("password");
        if (!findUserPassword.equals(password)) {
            throw new IllegalStateException("비밀번호가 올바르지 않습니다.");
        }

        // 해당 유저의 session 발급
        HttpSession session = req.getSession();
        session.setAttribute("user", findUser);
        resp.sendRedirect("/");
    }
}
