package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

public class StatsController {


    private ObservableList<Person> playerData = FXCollections.observableArrayList();

    @FXML
    private PieChart partiesJoues;
    @FXML
    private PieChart manchesJoues;
    @FXML
    private PieChart pointsJoues;

    @FXML
    private LineChart deltaPoints;
    @FXML
    private LineChart deltaManches;
    @FXML
    private LineChart deltaGames;
    @FXML
    private BarChart blanchissage;

    @FXML
    private Button stats_get_data;


    public StatsController(){
    }

    @FXML
    private void initialize(){


        stats_get_data.setOnAction((event)-> {
            SQL_ctrler sc = new SQL_ctrler();
            GameStats gs = new GameStats();
            String pl1 = Context.getInstance().currentPlayer1().getLastName();
            String pl2 = Context.getInstance().currentPlayer2().getLastName();
            gs = sc.getGame(pl1, pl2);
            int p1m = gs.getPlayer1_manches();
            int p2m = gs.getPlayer2_manches();
            int p1p = gs.getPlayer1_parties();
            int p2p = gs.getPlayer2_parties();
            int np = gs.getParties_nulles();
            int p1pts = gs.getPlayer1_points();
            int p2pts = gs.getPlayer2_points();

            ObservableList<PieChart.Data> pieChartData =
                    FXCollections.observableArrayList(
                            new PieChart.Data(pl1 + " " + Integer.toString(p1p), p1p),
                            new PieChart.Data(pl2 + " " + Integer.toString(p2p), p2p),
                            new PieChart.Data("nulles " + Integer.toString(np), np));
            partiesJoues.setData(pieChartData);
            //manchesJoues
            ObservableList<PieChart.Data> pieChartData2 =
                    FXCollections.observableArrayList(
                            new PieChart.Data(pl1 + " " + Integer.toString(p1m), p1m),
                            new PieChart.Data(pl2 + " " + Integer.toString(p2m), p2m));
            manchesJoues.setData(pieChartData2);
            ObservableList<PieChart.Data> pieChartData3 =
                    FXCollections.observableArrayList(
                            new PieChart.Data(pl1 + " " + Integer.toString(p1pts), p1pts),
                            new PieChart.Data(pl2 + " " + Integer.toString(p2pts), p2pts));
            pointsJoues.setData(pieChartData3);
            XYChart.Series<String, Integer> series = gs.getDeltapoints();
            series.setName("dPoints");
            deltaPoints.getData().add(series);
            XYChart.Series<String, Integer> series2 = gs.getDeltagames();
            series2.setName("dGames");
            deltaGames.getData().add(series2);
            XYChart.Series<String, Integer> series3 = gs.getDeltamanches();
            series3.setName("dManches");
            deltaManches.getData().add(series3);
            XYChart.Series<String, Integer> series4 = new XYChart.Series<>();
            series4.setName("Blanchissage");
            series4.getData().add(new XYChart.Data<>(pl1, gs.getPlayer1_blanchissage()));
            series4.getData().add(new XYChart.Data<>(pl2, gs.getPlayer2_blanchissage()));
            blanchissage.getData().add(series4);
        });
    }

}
