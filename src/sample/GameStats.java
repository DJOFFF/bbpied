package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameStats {

    private int player1_parties = 0;
    private int player2_parties = 0;
    private int player1_points = 0;
    private int player2_points = 0;
    private int parties_nulles = 0;
    private int player1_manches = 0;
    private int player2_manches = 0;
    private int player1_blanchissage = 0;
    private int player2_blanchissage = 0;
    private ObservableList<DeltaPoint> deltapoints = FXCollections.observableArrayList();

    public GameStats(){};

    public void analyzeGame(String Game){
        List<String> splittedGame = new ArrayList();
        if(Game.contains(";"))
            splittedGame = Arrays.asList(Game.split(";"));
        else
            splittedGame.add(Game);
    }

    public void addPlayer1Parties(){
        player1_parties += 1;
    }

    public void addPlayer2Parties(){
        player2_parties += 1;
    }

    public void addPartiesNulles(){
        parties_nulles += 1;
    }

    public void addPlayer1Manches(int manches){
        player1_manches += manches;
    }

    public void addPlayer2Manches(int manches){
        player2_manches += manches;
    }

    public void addPlayer1Points(int points){
        player1_points += points;
    }

    public void addPlayer2Points(int points){
        player2_points += points;
    }

    public void setDeltaPOints(DeltaPoint dp){
        deltapoints.add(dp);
    }

    public int getPlayer1_parties(){
        return player1_parties;
    }

    public int getPlayer2_parties(){
        return player2_parties;
    }

    public int getParties_nulles() {
        return parties_nulles;
    }

    public int getPlayer1_manches() {
        return player1_manches;
    }

    public int getPlayer2_manches() {
        return player2_manches;
    }

    public int getPlayer1_points() {
        return player1_points;
    }

    public int getPlayer2_points() {
        return player2_points;
    }

    public ObservableList<DeltaPoint> getDeltapoints() {
        return deltapoints;
    }
}
