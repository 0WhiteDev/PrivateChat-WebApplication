package uk.whitedev.chat.db;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.whitedev.chat.object.UserObject;
import uk.whitedev.chat.utils.Encryption;
import uk.whitedev.chat.utils.TokenStatus;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserRequest {

    private final DataSource dataSource;

    public UserRequest() throws NamingException {
        this.dataSource = DataSourceProvider.getDataSource();
    }

    public Optional<UserObject> findUserById(int id){
        String sql = "SELECT username, email, password, token FROM user WHERE iduser = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                String username = resultSet.getString("username");
                String email = Encryption.decrypt(resultSet.getString("email"));
                String password = Encryption.decrypt(resultSet.getString("password"));
                String token = Encryption.decrypt(resultSet.getString("token"));
                return Optional.of(new UserObject(id, username, email, password, token));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public boolean setNewPassword(String oldPassword, String newPassword){
        String sql = "UPDATE user SET password = ? WHERE password = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, Encryption.encrypt(newPassword));
            statement.setString(2, Encryption.encrypt(oldPassword));
            statement.executeUpdate();
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    public boolean tryNewUsername(String oldUsername, String newUsername) throws NamingException {
        LoginRequest loginRequest = new LoginRequest();
        switch (loginRequest.findIfUserExist(newUsername, "nomail", false)){
            case EXIST -> {
                return false;
            }
            case NOT_EXIST -> {
                setNewUsername(oldUsername, newUsername);
                return true;
            }
        }
        return false;
    }

    private void setNewUsername(String oldUsername, String newUsername){
        String sql = "UPDATE user SET username = ? WHERE username = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newUsername);
            statement.setString(2, oldUsername);
            statement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int findIdByToken(String token){
        String sql = "SELECT iduser FROM user WHERE token = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, Encryption.encrypt(token));
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt("iduser");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
    public TokenStatus findMatchingToken(String token){
        String sql = "SELECT username FROM user WHERE token = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, Encryption.encrypt(token));
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return TokenStatus.CORECT;
            }else{
                return TokenStatus.INVALID;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
