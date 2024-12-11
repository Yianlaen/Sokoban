package model;

public class User implements java.io.Serializable {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean checkLogin(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void save(MapMatrix savedata, int savedLevel, int savedSteps) {
        Accounts.replaceSaveByName(username, savedata, savedLevel, savedSteps);
    }

    public boolean hasSavedata() {
        return Accounts.findSaveByName(username) != null;
    }

    public Save getSave() {
        return Accounts.findSaveByName(username);
    }
}
