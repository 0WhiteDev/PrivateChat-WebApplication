package uk.whitedev.chat.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.whitedev.chat.logic.chatlogic.LoginSystem;
import uk.whitedev.chat.utils.TokenActions;

import javax.naming.NamingException;
import java.io.*;
import java.util.Optional;


@WebServlet("/login")
public class LoginController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            LoginSystem loginSystem = new LoginSystem();
            boolean auth = loginSystem.forwardAndTokenCheck(request, response, true);
            if(auth){
                response.sendRedirect(request.getContextPath() + "/app/home");
            }else if (!TokenActions.getTokenFromCookie(request).equals("no-token")){
                Optional<Cookie> cookieOptional = loginSystem.removeInvalidTokenCookie(request);
                cookieOptional.ifPresent(response::addCookie);
                request.setAttribute("info", "Invalid token!");
                request.getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
            }else{
                request.setAttribute("info", "");
                request.getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
            }
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            LoginSystem loginSystem = new LoginSystem();
            loginSystem.loginUser(request, response);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
}