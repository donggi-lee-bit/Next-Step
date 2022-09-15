package next.web;

import core.db.DataBase;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user/update")
public class UpdateUserFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        req.setAttribute("users", DataBase.findAll());
        RequestDispatcher rd = req.getRequestDispatcher("/user/update.html");
        rd.forward(req, resp);
    }
}
