package next.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LogoutServlet implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return "redirect:/users/loginForm";
    }
}
