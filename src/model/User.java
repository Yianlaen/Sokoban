package model;

public class User implements java.io.Serializable {
    private String username;
    private String password;
    private MapMatrix savedata;
    private int savedLevel;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.savedata = null;
        this.savedLevel = -1;
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

    public void save(MapMatrix savedata, int savedLevel) {
        this.savedata = savedata;
        this.savedLevel = savedLevel;
        Accounts.save();
    }

    public boolean hasSavedata() {
        return this.savedata != null;
    }

    public void clearSavedata() {
        this.savedata = null;
        this.savedLevel = -1;
        Accounts.save();
    }

    public MapMatrix loadSavedata() {
        return this.savedata;
    }

    public int loadSavedLevel() {
        return this.savedLevel;
    }

    public String toString() {
        return "[usr] " + this.username + " [pwd] " + this.password + " [lvl] " + this.savedLevel + " [map]:\n" + this.savedata;
    }
}
