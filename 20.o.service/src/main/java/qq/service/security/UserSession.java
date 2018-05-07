package qq.service.security;

import java.util.Objects;

public class UserSession {
    private Long userId;
    private String username;

    public UserSession() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
