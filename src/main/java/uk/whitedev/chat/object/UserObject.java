package uk.whitedev.chat.object;

public class UserObject {
    private int userId;
    private String username;
    private String email;
    private String password;
    private String token;

    public UserObject(int userId, String username, String email, String password, String token) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email; }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }
}
