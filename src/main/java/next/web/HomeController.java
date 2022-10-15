package next.web;

import static core.db.DataBase.findAll;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeController implements Controller{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        request.setAttribute("users", findAll());
        return "index.jsp";
    }
}
