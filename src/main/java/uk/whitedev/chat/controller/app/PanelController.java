package uk.whitedev.chat.controller.app;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.whitedev.chat.logic.chatlogic.LoginSystem;
import uk.whitedev.chat.logic.UserSystem;

import javax.naming.NamingException;
import java.io.IOException;
import java.util.Optional;


@WebServlet("/app/panel")
public class PanelController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            LoginSystem loginSystem = new LoginSystem();
            boolean auth = loginSystem.forwardAndTokenCheck(request, response, false);
            if (auth) {
                request.setAttribute("info", "");
                request.getRequestDispatcher("/WEB-INF/chat/panel.jsp").forward(request, response);
            } else {
                Optional<Cookie> cookieOptional = loginSystem.removeInvalidTokenCookie(request);
                cookieOptional.ifPresent(response::addCookie);
                request.setAttribute("info", "Invalid token!");
                request.getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
            }
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newUsername = request.getParameter("new-username");
        String oldPassword = request.getParameter("old-password");
        String newPassword = request.getParameter("new-password");
        try {
            UserSystem userSystem = new UserSystem();
            if (newUsername != null && !newUsername.isEmpty()) {
                if (userSystem.setNewUsername(newUsername, request)) {
                    usernameAction(request, response, newUsername, true);
                }else{
                    usernameAction(request, response, newUsername, false);
                }
            }else if(!oldPassword.isEmpty() && !newPassword.isEmpty()){
               if(userSystem.setNewPassword(oldPassword, newPassword, request)){
                   passwordAction(request, response, true);
               }else{
                   passwordAction(request, response, false);
               }
            }
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }


    private void usernameAction(HttpServletRequest request, HttpServletResponse response, String username, boolean isChanged) throws ServletException, IOException {
        if(isChanged) {
            request.setAttribute("info", String.format("Your Username was changed to %s", username));
            request.getRequestDispatcher("/WEB-INF/chat/panel.jsp").forward(request, response);
        }else{
            request.setAttribute("info", "This nickname is already taken");
            request.getRequestDispatcher("/WEB-INF/chat/panel.jsp").forward(request, response);
        }
    }

    private void passwordAction(HttpServletRequest request, HttpServletResponse response, boolean isChanged) throws ServletException, IOException {
        if(isChanged) {
            request.setAttribute("info", "Your Password was changed");
            request.getRequestDispatcher("/WEB-INF/chat/panel.jsp").forward(request, response);
        }else{
            request.setAttribute("info", "You entered the wrong password");
            request.getRequestDispatcher("/WEB-INF/chat/panel.jsp").forward(request, response);
        }
    }

}