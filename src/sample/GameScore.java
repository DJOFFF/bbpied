package sample;

import javafx.beans.property.*;

public class GameScore {
    private final SimpleStringProperty score1;
    private final SimpleStringProperty score2;

    public GameScore(String s1, String s2){
        this.score1 = new SimpleStringProperty(s1);
        this.score2 = new SimpleStringProperty(s2);
    }

    public void setScore(String s1, String s2){
        score1.set(s1);
        score2.set(s2);
    }
    public void setScore1(String s1){
        score1.set(s1);
    }

    public void setScore2(String s2){
        score2.set(s2);
    }

    public String getScore1(){
        return score1.get();
    }

    public String getScore2(){
        return score2.get();
    }
}
