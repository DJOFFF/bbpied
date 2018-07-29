package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PointsGui {

    private ObservableList<GameScore> game_score = FXCollections.observableArrayList();
    private int number_of_games;

    public PointsGui(int numberGame, String player1, String player2,ObservableList<GameScore> gs){
        game_score = gs;
        number_of_games=numberGame;

        Group root = new Group();
        Stage stage = new Stage();
        stage.setTitle("Points");
        stage.setScene(new Scene(root, 400,250));
        GridPane mon_gridPane = new GridPane();
        mon_gridPane.setVgap(10);
        mon_gridPane.setHgap(10);

        int row = 0;

        // add player name:
        Text text_player1 = new Text(player1);
        Text text_player2 = new Text(player2);
        mon_gridPane.add(text_player1,1,row);
        mon_gridPane.add(text_player2,2,row);
        row++;

        TextField[] scores = new TextField[number_of_games*2];
        Text[] score_label = new Text[number_of_games];
        for(int i = 0; i<number_of_games; i++){
            score_label[i] = new Text("Game "+Integer.toString(i+1));
            scores[i*2+0] = new TextField();
            scores[i*2+1] = new TextField();
            mon_gridPane.add(score_label[i], 0, row);
            mon_gridPane.add(scores[i*2+0], 1, row);
            mon_gridPane.add(scores[i*2+1], 2, row);
            row++;
        }
        Button my_button = new Button();
        my_button.setText("Ok");
        mon_gridPane.add(my_button,0,row);
        my_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for(int i = 0; i<number_of_games;i++){
                    game_score.add(new GameScore(scores[2*i+0].getText(), scores[2*i+1].getText()));
                }
                stage.close();
            }
        });


        root.getChildren().add(mon_gridPane);
        stage.show();

        //todo : protection sur les games.
    }
}
