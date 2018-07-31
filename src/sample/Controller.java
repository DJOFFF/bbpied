package sample;

import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.awt.*;

public class Controller {

    @FXML
    private Button bouton_nb_game;

    @FXML
    private Label label_enter_points;

    @FXML
    private ComboBox<Person> player1;
    @FXML
    private ComboBox<Person> player2;
    private ObservableList<Person> playerData = FXCollections.observableArrayList();

    @FXML
    private TableView<GameScore> pointsTable;
    private ObservableList<GameScore> game_score = FXCollections.observableArrayList();
    @FXML
    private TableColumn<GameScore, String> pointsTable_col1;
    @FXML
    private TableColumn<GameScore, String> pointsTable_col2;

    @FXML
    private TextField nb_games;

    @FXML
    private DatePicker add_date;

    @FXML
    private Button addToDB;

    @FXML
    private PieChart partiesJoues;
    @FXML
    private PieChart manchesJoues;
    @FXML
    private PieChart pointsJoues;

    //@FXML
    //private LineChart<> statsLineChart;

    @FXML
    private Button stats_get_data;


    public Controller(){
        playerData.add(new Person("Joffrey"));
        playerData.add(new Person("JPB"));
        playerData.add(new Person("player3"));
        playerData.add(new Person("player4"));
    }

    @FXML
    private void initialize(){
        PropertyValueFactory<GameScore, String> score1Property = new PropertyValueFactory<>("score1");
        PropertyValueFactory<GameScore, String> score2Property = new PropertyValueFactory<>("score2");
        pointsTable_col1.setCellValueFactory(score1Property);
        pointsTable_col2.setCellValueFactory(score2Property);
        pointsTable.setEditable(true);
        bouton_nb_game.setOnAction((event) -> {
            String mon_nb_games = nb_games.getText();
            if (mon_nb_games.isEmpty() != true){
                int int_nb_games = Integer.parseInt(mon_nb_games);
                game_score.clear();
                PointsGui mon_points_gui = new PointsGui(int_nb_games,
                        player1.getSelectionModel().getSelectedItem().getLastName(),
                        player2.getSelectionModel().getSelectedItem().getLastName(),
                        game_score);
                pointsTable.setItems(game_score);
            }
        });
//
//        lastNameCol.setOnEditCommit(
//                (TableColumn.CellEditEvent<Person, String> t) -> {
//                    ((Person) t.getTableView().getItems().get(
//                            t.getTablePosition().getRow())
//                    ).setLastName(t.getNewValue());
//                });
        pointsTable_col1.setOnEditCommit((TableColumn.CellEditEvent<GameScore, String> t) ->{
            ((GameScore) t.getTableView().getItems().get(t.getTablePosition().getRow())).setScore1(t.getNewValue());
        });
//        pointsTable_col1.setOnEditCommit(event -> {
//            GameScore edit_gs = pointsTable.getItems();
//
//        });

        player1.setItems(playerData);
        player1.setCellFactory((combobox) -> {
            return new ListCell<Person>(){
                @Override
                protected void updateItem(Person item, boolean empty){
                    super.updateItem(item, empty);

                    if (item == null || empty){
                        setText(null);
                    } else{
                        setText(item.getLastName());
                    }
                }
            };
        });
        player1.setConverter(new StringConverter<Person>() {
            @Override
            public String toString(Person object) {
                if (object == null){
                    return null;
                } else {
                    return object.getLastName();
                }
            }

            @Override
            public Person fromString(String string) {
                return null;
            }
        });
        player1.setOnAction((event)->{
            Person selectedPerson = player1.getSelectionModel().getSelectedItem();
            pointsTable_col1.setText(selectedPerson.getLastName());
        });

        player2.setItems(playerData);
        player2.setCellFactory((combobox) -> {
            return new ListCell<Person>(){
                @Override
                protected void updateItem(Person item, boolean empty){
                    super.updateItem(item, empty);

                    if (item == null || empty){
                        setText(null);
                    } else{
                        setText(item.getLastName());
                    }
                }
            };
        });
        player2.setConverter(new StringConverter<Person>() {
            @Override
            public String toString(Person object) {
                if (object == null){
                    return null;
                } else {
                    return object.getLastName();
                }
            }

            @Override
            public Person fromString(String string) {
                return null;
            }
        });
        player2.setOnAction((event)-> {
                    Person selectedPerson = player2.getSelectionModel().getSelectedItem();
                    pointsTable_col2.setText(selectedPerson.getLastName());
                });
        stats_get_data.setOnAction((event)->{
            SQL_ctrler sc = new SQL_ctrler();
            GameStats gs = new GameStats();
            String pl1 = player1.getSelectionModel().getSelectedItem().getLastName();
            String pl2 = player2.getSelectionModel().getSelectedItem().getLastName();
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
            }
        );

        addToDB.setOnAction((event) -> {
            SQL_ctrler sql_ctrler = new SQL_ctrler();
            java.sql.Date sqlDate =java.sql.Date.valueOf(add_date.getValue());
            sql_ctrler.setGame(sqlDate,
            player1.getSelectionModel().getSelectedItem().getLastName(),
            player2.getSelectionModel().getSelectedItem().getLastName(), game_score);
        });



    }

}
