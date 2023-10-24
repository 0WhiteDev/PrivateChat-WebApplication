package uk.whitedev.chat.logic.chatlogic;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.whitedev.chat.db.ChannelRequest;
import uk.whitedev.chat.db.LoginRequest;
import uk.whitedev.chat.db.UserRequest;
import uk.whitedev.chat.object.MessagesObject;
import uk.whitedev.chat.object.UserObject;
import uk.whitedev.chat.utils.TokenActions;

import javax.naming.NamingException;
import java.io.IOException;
import java.util.Optional;

public class MessageSystem {

    public void sendChatInfoToClient(HttpServletRequest request, HttpServletResponse response) throws NamingException, ServletException, IOException {
        ChannelRequest channelRequest = new ChannelRequest();
        UserRequest userRequest = new UserRequest();
        String param = request.getParameter("chatroom-id");
        MessagesObject[] messages = new ChannelRequest().getMessages(param);
        int id = userRequest.findIdByToken(TokenActions.getTokenFromCookie(request));
        Optional<UserObject> userObject = userRequest.findUserById(id);
        userObject.ifPresent(o -> request.setAttribute("username", o.getUsername()));
        Optional<String> channelName = channelRequest.findChannelNameById(Integer.parseInt(param));
        channelName.ifPresent(s -> request.setAttribute("channel-name", s));
        request.setAttribute("messages", messages);
        request.setAttribute("channel-id", param);
        request.getRequestDispatcher("/WEB-INF/chat/chatroom.jsp").forward(request, response);
    }

    public boolean checkIfChannelExist(int id) throws NamingException {
        ChannelRequest channelRequest = new ChannelRequest();
        Optional<String> channelOpt = channelRequest.findChannelNameById(id);
        return channelOpt.isPresent();
    }

    public void sendMessageToDataBase(HttpServletRequest request) {
        String messageText = request.getParameter("message");
        if (messageText != null && !messageText.isEmpty()) {
            try {
                ChannelRequest channelRequest = new ChannelRequest();
                UserRequest userRequest = new UserRequest();
                String channelName = "Unnamed Channel";
                int channelId = Integer.parseInt(request.getParameter("chatroom-id"));
                Optional<String> channelNameOpt = channelRequest.findChannelNameById(channelId);
                if (channelNameOpt.isPresent()) channelName = channelNameOpt.get();
                int userId = userRequest.findIdByToken(TokenActions.getTokenFromCookie(request));
                channelRequest.sendMessage(userId, channelId, channelName, messageText);
            } catch (NamingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
