package edu.bbte.idde.csim2126.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebFilter(servletNames = {"MenuWebServlet"})
public class AuthFilter extends HttpFilter {
    @Override
    public void init(FilterConfig config) throws ServletException {}

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpSession session = req.getSession();

        if (session.getAttribute("username") == null) {
            res.sendRedirect(req.getContextPath() + "/login.html");
            log.info("Not logged in yet!");
        } else {
            chain.doFilter(req, res);
            log.info("Logged in!");
        }

    }

}
