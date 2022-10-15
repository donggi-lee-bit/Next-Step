package next.web;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private RequestMapping requestMapping;

    @Override
    public void init() throws ServletException {
        requestMapping = new RequestMapping();
        requestMapping.init();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        logger.debug("Method: {}, RequestURI: {}", req.getMethod(), requestURI);
        Controller controller = requestMapping.getController(requestURI);
        try {
            String viewName = controller.execute(req, resp);
            redirect(viewName, req, resp);
        } catch (Throwable throwable) {
            logger.error("RequestMapping Error: {}", throwable);
            throw new ServletException(throwable.getMessage());
        }
    }

    private void redirect(String viewName, HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            resp.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return;
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(viewName);
        requestDispatcher.forward(req, resp);
    }
}
