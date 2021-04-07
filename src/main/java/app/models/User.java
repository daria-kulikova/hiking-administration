package app.models;

public class User {

    private String userName;
    private String RoleName;

    public User(String userName, String roleName) {
        this.userName = userName;
        RoleName = roleName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }
}
