package uk.whitedev.chat.controller.app;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.whitedev.chat.db.ChannelRequest;
import uk.whitedev.chat.logic.chatlogic.LoginSystem;
import uk.whitedev.chat.logic.chatlogic.MessageSystem;
import uk.whitedev.chat.object.MessagesObject;

import javax.naming.NamingException;
import java.io.IOException;


@WebServlet("/app/chat")
public class ChatController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            LoginSystem loginSystem = new LoginSystem();
            MessageSystem messageSystem = new MessageSystem();
            boolean auth = loginSystem.forwardAndTokenCheck(request, response, true);
            if (auth) {
                String param = request.getParameter("chatroom-id");
                int chatroomId = 0;
                if (param != null && param.matches("\\d+")) {
                    chatroomId = Integer.parseInt(param);
                }
                if (messageSystem.checkIfChannelExist(chatroomId)) {
                    messageSystem.sendChatInfoToClient(request, response);
                } else {
                    request.setAttribute("info", "Can't find chatroom with this id!");
                    request.getRequestDispatcher("/WEB-INF/chat/app.jsp").forward(request, response);
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/login");
            }
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MessageSystem messageSystem = new MessageSystem();
        messageSystem.sendMessageToDataBase(request);
        String param = request.getParameter("chatroom-id");
        if (param != null && !param.isEmpty()) {
            try {
                MessagesObject[] messages = new ChannelRequest().getMessages(param);
                request.setAttribute("messages", messages);
                messageSystem.sendChatInfoToClient(request, response);
                request.getRequestDispatcher("/WEB-INF/chat/chatroom.jsp").forward(request, response);
            } catch (NamingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}