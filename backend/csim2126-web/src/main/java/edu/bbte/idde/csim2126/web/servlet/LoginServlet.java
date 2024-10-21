package edu.bbte.idde.csim2126.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final String username = "admin";
    private static final String password = "password";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (LoginServlet.username.equals(username) && LoginServlet.password.equals(password)) {
            session.setAttribute("username", username);
            resp.sendRedirect(req.getContextPath() + "/template-menus");
            log.info("POST /login {}", HttpServletResponse.SC_ACCEPTED);
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.sendRedirect(req.getContextPath() + "/login.html");
            log.info("POST /login {}", HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
