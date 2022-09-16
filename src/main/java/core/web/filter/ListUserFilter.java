package core.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import next.web.UpdateUserServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter("/user/list")
public class ListUserFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(UpdateUserServlet.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String initParameter = filterConfig.getInitParameter("ListUserFilter 동작중");
        System.out.println(initParameter);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        // session이 없으면 로그인이 안 된 상태로 인지, 로그인 창으로 redirect 시킨다.
        // todo 현재 필터 작동하지 않음. 애플리케이션을 실행할 때 No global web.xml found 이 문구를 통해 해답을 찾아보자
        log.info("이게 왜 안될까?");
        log.info("session: {}", session.getId());
        if (session == null || session.getAttribute("userId") == null) {
            httpResponse.sendRedirect("/user/login.jsp");
            log.info("세션이 왜 안 없어졌을까??");
        } else {
            log.info("왜 if문 동작 안함?");
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }

}
