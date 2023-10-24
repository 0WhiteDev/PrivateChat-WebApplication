package uk.whitedev.chat.logic.chatlogic;

import uk.whitedev.chat.db.ChannelRequest;
import uk.whitedev.chat.utils.TokenActions;

import javax.naming.NamingException;

public class RoomSystem {
    public int createNewRoom(String name) throws NamingException {
        ChannelRequest channelRequest = new ChannelRequest();
        int id = TokenActions.generateChannelId();
        if (name.length() > 45) {
            String truncatedString = name.substring(0, 45);
            channelRequest.createNewChannel(truncatedString, id);
            return id;
        } else {
            channelRequest.createNewChannel(name, id);
            return id;
        }
    }
}
