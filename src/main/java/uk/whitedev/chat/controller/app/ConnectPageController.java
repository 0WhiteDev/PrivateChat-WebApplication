package uk.whitedev.chat.controller.app;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.whitedev.chat.logic.chatlogic.LoginSystem;

import javax.naming.NamingException;
import java.io.IOException;


@WebServlet("/app/home")
public class ConnectPageController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            LoginSystem loginSystem = new LoginSystem();
            boolean auth = loginSystem.forwardAndTokenCheck(request, response, true);
            if(auth){
                request.setAttribute("info", "");
                request.getRequestDispatcher("/WEB-INF/chat/app.jsp").forward(request, response);
            }else{
                request.setAttribute("info", "Invalid token!");
                request.getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
            }
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
}