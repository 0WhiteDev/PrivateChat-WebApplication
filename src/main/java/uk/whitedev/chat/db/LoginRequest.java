package uk.whitedev.chat.db;

import uk.whitedev.chat.object.UserObject;
import uk.whitedev.chat.utils.Encryption;
import uk.whitedev.chat.utils.TokenActions;
import uk.whitedev.chat.utils.UserStatus;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class LoginRequest {

    private final DataSource dataSource;

    public LoginRequest() throws NamingException {
        this.dataSource = DataSourceProvider.getDataSource();
    }

    public String registerUser(String username, String email, String password){
        String sql = "INSERT INTO user (username, password, email, token) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            String newToken = TokenActions.generateToken();
            statement.setString(1, username);
            statement.setString(2, Encryption.encrypt(password));
            statement.setString(3, Encryption.encrypt(email));
            statement.setString(4, Encryption.encrypt(newToken));
            statement.executeUpdate();
            return newToken;
        } catch (SQLException e) {
            return "no-token";
        }
    }

    public UserStatus findIfUserExist(String username, String email, boolean withEmail){
        String sql;
        if(withEmail) {
            sql = "SELECT username FROM user WHERE username = ? OR email = ?";
        }else{
            sql = "SELECT username, token FROM user WHERE username = ?";
        }
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            if(withEmail) statement.setString(2, Encryption.encrypt(email));
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                System.out.println(Encryption.decrypt(resultSet.getString("username")));
                System.out.println(Encryption.decrypt(resultSet.getString("token")));
                return UserStatus.EXIST;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return UserStatus.NOT_EXIST;
    }

    public Optional<UserObject> getUserByName(String username, String password){
        String sql = "SELECT iduser, username, password, email, token FROM user WHERE username = ? AND password = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, Encryption.encrypt(password));
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                int resultId = resultSet.getInt("iduser");
                String resultUsername = resultSet.getString("username");
                String resultEmail = Encryption.decrypt(resultSet.getString("email"));
                String resultPassword = Encryption.decrypt(resultSet.getString("password"));
                String resultToken = Encryption.decrypt(resultSet.getString("token"));
                return Optional.of(new UserObject(resultId, resultUsername, resultEmail, resultPassword, resultToken));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
