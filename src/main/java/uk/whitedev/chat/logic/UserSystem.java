package uk.whitedev.chat.logic;

import jakarta.servlet.http.HttpServletRequest;
import uk.whitedev.chat.db.UserRequest;
import uk.whitedev.chat.object.UserObject;
import uk.whitedev.chat.utils.TokenActions;

import javax.naming.NamingException;
import java.util.Optional;

public class UserSystem {
    public boolean setNewUsername(String newUsername, HttpServletRequest request) throws NamingException {
        UserRequest userRequest = new UserRequest();
        int id = userRequest.findIdByToken(TokenActions.getTokenFromCookie(request));
        boolean isChanged = false;
        Optional<UserObject> userStatusOpt = userRequest.findUserById(id);
        if(userStatusOpt.isPresent()){
            isChanged = userRequest.tryNewUsername(userStatusOpt.get().getUsername(), newUsername);
        }
        return isChanged;
    }

    public boolean setNewPassword(String oldPassword, String newPassword, HttpServletRequest request) throws NamingException {
        UserRequest userRequest = new UserRequest();
        int id = userRequest.findIdByToken(TokenActions.getTokenFromCookie(request));
        boolean isChanged = false;
        Optional<UserObject> userStatusOpt = userRequest.findUserById(id);
        if(userStatusOpt.isPresent() && oldPassword.equals(userStatusOpt.get().getPassword())){
            isChanged = userRequest.setNewPassword(oldPassword, newPassword);
        }
        return isChanged;
    }
}
