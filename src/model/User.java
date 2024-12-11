package model;

public class User implements java.io.Serializable {
    private String username;
    private String password;
    private MapMatrix savedMap;
    private int savedLevelId;
    private int savedSteps;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.savedMap = null;
        this.savedLevelId = -1;
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
        this.savedMap = savedata;
        this.savedLevelId = savedLevel;
        this.savedSteps = savedSteps;
        Accounts.save();
    }

    public boolean hasSavedata() {
        return this.savedMap != null;
    }

    public void clearSavedata() {
        this.savedMap = null;
        this.savedLevelId = -1;
        this.savedSteps = 0;
        Accounts.save();
    }

    public MapMatrix loadSavedMap() {
        return this.savedMap;
    }

    public int loadSavedLevelId() {
        return this.savedLevelId;
    }

    public int loadSavedSteps() {
        return this.savedSteps;
    }

    public String toString() {
        return "[usr] " + this.username + " [pwd] " + this.password + " [lvl] " + this.savedLevelId + " [steps] "
                + this.savedSteps + " [map]:\n" + this.savedMap;
    }
}
