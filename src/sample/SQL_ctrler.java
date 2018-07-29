package sample;

import javafx.collections.ObservableList;

import javax.xml.transform.Result;
import java.sql.*;

import static jdk.nashorn.internal.objects.Global.print;
import static jdk.nashorn.internal.objects.Global.println;

public class SQL_ctrler{

    private Connection c = null;
    private Statement stmt = null;

    public SQL_ctrler() {
                try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:bbfoot.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public void setGame(java.sql.Date date,String player1, String player2, ObservableList<GameScore> gs){
        int p1SumPoints = 0;
        int p2SumPoints = 0;
        int p1SumManches = 0;
        int p2SumManches = 0;
        String p1ScoreTemp;
        String p2ScoreTemp;
        int p1ScoreTempInt = 0;
        int p2ScoreTempInt = 0;

        String pointage = new String();

        for(GameScore gstemp: gs){
            p1ScoreTemp = gstemp.getScore1();
            p2ScoreTemp = gstemp.getScore2();
            p1ScoreTempInt = Integer.parseInt(p1ScoreTemp);
            p2ScoreTempInt = Integer.parseInt(p2ScoreTemp);

            p1SumPoints += p1ScoreTempInt;
            p2SumPoints += p2ScoreTempInt;

            pointage += p1ScoreTemp + "-" + p2ScoreTemp + ";";

            if(p1ScoreTempInt > p2ScoreTempInt){
                p1SumManches += 1;
            }
            else{
                p2SumManches += 1;
            }
        }
        pointage = pointage.substring(0, pointage.length() - 1);

        try{
            stmt = c.createStatement();
            String sql = "INSERT INTO Game (Date, Player1, Player2, Score, TotalMancheP1, TotalMancheP2, TotalPointP1, TotalPointP2)"
                    + "VALUES ('"+ date+"','" + player1 + "','" + player2 + "','" + pointage + "'," + Integer.toString(p1SumManches) +","+
                    Integer.toString(p2SumManches) + "," + Integer.toString(p1SumPoints) + "," + Integer.toString(p2SumPoints) + ");";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public GameStats getGame(String player1, String player2){
        GameStats gs = new GameStats();

        String sql = "SELECT * FROM Game WHERE " +
                "(Player1 = '" + player1 + "' OR " +
                "Player1 = '" + player2 + "') AND (" +
                "Player2 = '" + player1 + "' OR " +
                "Player2 = '" + player2 +"')";

        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                String p1name = rs.getString("Player1");
                String p2name = rs.getString("Player2");
                int p1manche = rs.getInt("TotalMancheP1");
                int p2manche = rs.getInt("TotalMancheP2");
                int p1point = rs.getInt("TotalPointP1");
                int p2point = rs.getInt("TotalPointP2");

                if(p1name.equals(player1)){
                    gs.addPlayer1Points(p1point);
                    gs.addPlayer2Points(p2point);
                    gs.addPlayer1Manches(p1manche);
                    gs.addPlayer2Manches(p2manche);
                    if(p1manche == p2manche){
                        gs.addPartiesNulles();
                    }
                    else if(p1manche>p2manche){
                        gs.addPlayer1Parties();
                    }
                    else{
                        gs.addPlayer2Parties();
                    }
                }
                else{
                    gs.addPlayer1Points(p2point);
                    gs.addPlayer2Points(p1point);
                    gs.addPlayer1Manches(p2manche);
                    gs.addPlayer2Manches(p1manche);
                    if(p1manche == p2manche){
                        gs.addPartiesNulles();
                    }
                    else if(p1manche>p2manche){
                        gs.addPlayer2Parties();
                    }
                    else{
                        gs.addPlayer1Parties();
                    }
                }

            }

        }catch( Exception e ){
            e.printStackTrace();
            }

        return gs;
    }
}
