import javafx.beans.property.SimpleStringProperty;

public class StatsModel {
    public SimpleStringProperty gameId = new SimpleStringProperty();
    public SimpleStringProperty playerName = new SimpleStringProperty();
    public SimpleStringProperty playerColor = new SimpleStringProperty();
    public SimpleStringProperty timeStart = new SimpleStringProperty();
    public SimpleStringProperty timeEnd = new SimpleStringProperty();
    public SimpleStringProperty duration = new SimpleStringProperty();
    public SimpleStringProperty comp1Settings = new SimpleStringProperty();
    public SimpleStringProperty comp2Settings = new SimpleStringProperty();
    public SimpleStringProperty comp3Settings = new SimpleStringProperty();
    public SimpleStringProperty winner = new SimpleStringProperty();

    public String getGameId() {
        return gameId.get();
    }

    public String getPlayerName() {
        return playerName.get();
    }

    public String getPlayerColor() {
        return playerColor.get();
    }

    public String getTimeStart() {
        return timeStart.get();
    }

    public String getTimeEnd() {
        return timeEnd.get();
    }

    public String getDuration() {
        return duration.get();
    }

    public String getComp1Settings() {
        return comp1Settings.get();
    }

    public String getComp2Settings() {
        return comp2Settings.get();
    }

    public String getComp3Settings() {
        return comp3Settings.get();
    }

    public String getWinner() {
        return winner.get();
    }
}
