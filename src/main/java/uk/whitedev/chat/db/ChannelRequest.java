package uk.whitedev.chat.db;

import uk.whitedev.chat.object.MessagesObject;
import uk.whitedev.chat.object.UserObject;
import uk.whitedev.chat.utils.Encryption;
import uk.whitedev.chat.utils.TokenActions;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ChannelRequest {
    private final DataSource dataSource;
    private final UserRequest userRequest;

    public ChannelRequest() throws NamingException {
        this.dataSource = DataSourceProvider.getDataSource();
        this.userRequest = new UserRequest();
    }

    public void sendMessage(int id, int channelId, String channelName, String message){
        String sql = "INSERT INTO chatroom (id, name, message, userid) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, channelId);
            statement.setString(2, channelName);
            statement.setString(3, Encryption.encrypt(message));
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createNewChannel(String name, int id){
        String sql = "INSERT INTO chatroomid (id, name) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<String> findChannelNameById(int id){
        String sql = "SELECT name  FROM chatroomid WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                String name = resultSet.getString("name");
                return Optional.of(name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public MessagesObject[] getMessages(String channelid) {
        MessagesObject[] messagesObjects = new MessagesObject[1000];
        int index = 0;
        String sql = "SELECT userid, message  FROM chatroom WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, channelid);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int resultId = resultSet.getInt("userid");
                String resultMessage = Encryption.decrypt(resultSet.getString("message"));
                Optional<UserObject> userObject = userRequest.findUserById(resultId);
                if(userObject.isPresent()) messagesObjects[index] = new MessagesObject(userObject.get().getUsername(), resultMessage);
                index++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return messagesObjects;
    }
}
