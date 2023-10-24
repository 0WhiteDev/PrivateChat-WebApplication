package uk.whitedev.chat.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.whitedev.chat.logic.chatlogic.LoginSystem;

import javax.naming.NamingException;
import java.io.IOException;
import java.util.Optional;


@WebServlet("/app/logout")
public class LogoutController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token-cookie")) {
                    Cookie cookie1 = new Cookie("token-cookie", "no-token");
                    cookie1.setMaxAge(0);
                    cookie1.setPath("/");
                    response.addCookie(cookie1);
                    request.setAttribute("info", "You have been logged out");
                    request.getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
                }
            }
        }
        request.setAttribute("info", "You have no sessions to log out of!");
        request.getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("info", "");
        request.getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
    }
}