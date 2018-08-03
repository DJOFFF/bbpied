package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

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
    private XYChart.Series<String, Integer> deltapoints = new XYChart.Series<>();
    private XYChart.Series<String, Integer> deltamanches = new XYChart.Series<>();
    private XYChart.Series<String, Integer> deltagames = new XYChart.Series<>();
    private int iteration = 0;
    private int deltaMancheCumulative = 0;
    private int deltaPointsCumulative = 0;
    private int deltaGamesCumulative = 0;

    public GameStats(){};

    public void analyzeGame(String Game, boolean inv){
        List<String> splittedGame = new ArrayList();
        int i = 0;
        if(Game.contains(";"))
            splittedGame = Arrays.asList(Game.split(";"));
        else
            splittedGame.add(Game);

        for(String item:splittedGame){
            if (item.contains("0")){
                String[] splitted_manche = item.split("-");
                if (splitted_manche[0].equals("0")){
                    if (inv == false){
                        addPlayer2Blanchissage();
                    } else{
                        addPlayer1Blanchissagge();
                    }
                }else{
                    if (inv == false){
                        addPlayer2Blanchissage();
                    } else{
                        addPlayer1Blanchissagge();
                    }
                }
            }
        }
    }

    public void addData(int p1Points, int p2Points, int p1Manches, int p2Manches, String score, boolean inv){

        this.analyzeGame(score, inv);
        this.addPlayer1Points(p1Points);
        this.addPlayer2Points(p2Points);
        this.addPlayer1Manches(p1Manches);
        this.addPlayer2Manches(p2Manches);
        deltaMancheCumulative += p1Manches-p2Manches;
        this.setDeltaManches(iteration, deltaMancheCumulative);
        deltaPointsCumulative += p1Points-p2Points;
        this.setDeltaPoints(iteration, deltaPointsCumulative);
        if(p1Manches == p2Manches){
            this.addPartiesNulles();
        }
        else if(p1Manches>p2Manches){
            this.addPlayer1Parties();
            deltaGamesCumulative += 1;
        }
        else{
            this.addPlayer2Parties();
            deltaGamesCumulative -= 1;
        }
        this.setDeltaGames(iteration, deltaGamesCumulative);
        iteration++;
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

    public void addPlayer1Blanchissagge(){
        player1_blanchissage +=1;
    }

    public void addPlayer2Blanchissage(){
        player2_blanchissage +=1;
    }

    public void setDeltaPoints(int x, int y){
        deltapoints.getData().add(new XYChart.Data(Integer.toString(x),y));
    }

    public void setDeltaManches(int x, int y){
        deltamanches.getData().add(new XYChart.Data<>(Integer.toString(x), y));
    }

    public void setDeltaGames(int x, int y){
        deltagames.getData().add(new XYChart.Data<>(Integer.toString(x), y));
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

    public int getPlayer1_blanchissage() {
        return player1_blanchissage;
    }

    public int getPlayer2_blanchissage() {
        return player2_blanchissage;
    }

    public XYChart.Series<String, Integer> getDeltapoints() {
        return deltapoints;
    }

    public XYChart.Series<String, Integer> getDeltamanches() {
        return deltamanches;
    }

    public XYChart.Series<String, Integer> getDeltagames() {
        return deltagames;
    }
}
