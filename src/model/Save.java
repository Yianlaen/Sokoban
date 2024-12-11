package model;

public class Save implements java.io.Serializable {
    private String username;
    private MapMatrix savedMap;
    private int savedLevelId;
    private int savedSteps;

    public Save(String username, MapMatrix savedMap, int savedLevelId, int savedSteps) {
        this.username = username;
        this.savedMap = savedMap;
        this.savedLevelId = savedLevelId;
        this.savedSteps = savedSteps;
    }

    public String getUsername() {
        return this.username;
    }

    public MapMatrix getSavedMap() {
        return this.savedMap;
    }

    public int getSavedLevelId() {
        return this.savedLevelId;
    }

    public int getSavedSteps() {
        return this.savedSteps;
    }

    public void setSavedMap(MapMatrix savedMap) {
        this.savedMap = savedMap;
    }

    public void setSavedLevelId(int savedLevelId) {
        this.savedLevelId = savedLevelId;
    }

    public void setSavedSteps(int savedSteps) {
        this.savedSteps = savedSteps;
    }
}
