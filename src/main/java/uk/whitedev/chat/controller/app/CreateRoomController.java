package uk.whitedev.chat.controller.app;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.whitedev.chat.logic.chatlogic.LoginSystem;
import uk.whitedev.chat.logic.chatlogic.RoomSystem;

import javax.naming.NamingException;
import java.io.IOException;
import java.util.Optional;


@WebServlet("/app/create")
public class CreateRoomController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            LoginSystem loginSystem = new LoginSystem();
            boolean auth = loginSystem.forwardAndTokenCheck(request, response, false);
            if (auth) {
                request.getRequestDispatcher("/WEB-INF/chat/createroom.jsp").forward(request, response);
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
        String chatname = request.getParameter("chatname");
        if (chatname != null && !chatname.isEmpty()) {
            try {
                RoomSystem roomSystem = new RoomSystem();
                int id = roomSystem.createNewRoom(chatname);
                response.sendRedirect(request.getContextPath() + "/app/chat?chatroom-id=" + id);

            } catch (NamingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}