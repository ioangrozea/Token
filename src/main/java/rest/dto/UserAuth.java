package rest.dto;

import rest.Role;

public class UserAuth {
    private String userName;
    private String password;
    private Role role;

    public UserAuth(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public UserAuth() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
