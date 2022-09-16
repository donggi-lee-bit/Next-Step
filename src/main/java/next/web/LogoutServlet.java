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

@WebServlet("/user/logout")
public class LogoutServlet extends HttpServlet {
    // 로그아웃 버튼 클릭시 해당 유저의 session 을 삭제한다.
    // session이 없기 때문에 로그인 창으로 넘어가게한다.


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.removeAttribute("user");
        resp.sendRedirect("/user/list");
    }
}
