package giocoBrunettiMorini;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Labirinto extends Application {
    private static final int CELL_SIZE = 20;
    private static final int WIDTH = 30;
    private static final int HEIGHT = 27;
    private static final int TIME_LIMIT = 30; // secondi 

    private Rectangle giocatore;
    private int xGiocatore = 1;
    private int yGiocatore = 1;

    private int xArrivo = 28;
    private int yArrivo = 25;

    

    private int tempoRimasto = TIME_LIMIT;
    private Text tempo;

    private final int[][] labirinto = {
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,0,0,0,1,0,0,0,0,1,0,1,0,0,0,1,0,0,0,1,0,1,0,0,0,1,0,0,0,1},
        {1,0,1,0,1,0,1,1,0,1,0,1,1,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
        {1,0,1,0,0,0,0,1,0,0,0,0,0,1,0,1,0,1,0,0,0,1,0,0,0,0,0,1,0,1},
        {1,0,1,1,1,1,0,1,1,1,1,1,0,1,0,1,1,1,1,1,0,1,1,1,1,1,0,1,0,1},
        {1,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1},
        {1,1,1,1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,0,1,1,1,0,1},
        {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1},
        {1,0,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,0,1},
        {1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1,0,1},
        {1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,0,1,1,1},
        {1,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1},
        {1,0,1,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1},
        {1,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,1},
        {1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,0,1},
        {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1},
        {1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1},
        {1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,1},
        {1,0,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,0,1},
        {1,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1},
        {1,0,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1,0,1,1,1,1,1},
        {1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1},
        {1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1},
        {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,1},
        {1,0,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,0,1},
        {1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
    };

    @Override
    public void start(Stage stage) {
        Pane pannello = new Pane();
        
       
        Scene scene = new Scene(pannello, WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE + 60);
        
        Timeline timeline = new Timeline(new KeyFrame(
      	      Duration.seconds(1), // ogni quanto va chiamata la funzione
      	      x -> aggiornaTimer()));
      	    timeline.setCycleCount(100);
      	    timeline.play();
      

        tempo = new Text("Tempo rimasto: " + tempoRimasto);
        tempo.setFont(Font.font(20));
        tempo.setY(HEIGHT * CELL_SIZE + 30);
        pannello.getChildren().add(tempo);

        caricaLabirinto(pannello);

        giocatore = new Rectangle(CELL_SIZE, CELL_SIZE, Color.BLUE);
        giocatore.setX(xGiocatore * CELL_SIZE);
        giocatore.setY(yGiocatore * CELL_SIZE);

        scene.setOnKeyPressed(e -> {
            int dx = 0, dy = 0;
            if (e.getCode() == KeyCode.UP) dy = -1;
            else if (e.getCode() == KeyCode.DOWN) dy = 1;
            else if (e.getCode() == KeyCode.LEFT) dx = -1;
            else if (e.getCode() == KeyCode.RIGHT) dx = 1;

            int newX = xGiocatore + dx;
            int newY = yGiocatore + dy;

            if (labirinto[newY][newX] == 0) {
                xGiocatore = newX;
                yGiocatore = newY;
                giocatore.setX(xGiocatore * CELL_SIZE);
                giocatore.setY(yGiocatore * CELL_SIZE);
                vittoria(stage);
            }
        });

        pannello.getChildren().add(giocatore);
        
       

        stage.setScene(scene);
        stage.setTitle("Labirinto");
        stage.show();
    }

    private void caricaLabirinto(Pane root) {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Rectangle casella = new Rectangle(CELL_SIZE, CELL_SIZE);
                casella.setX(x * CELL_SIZE);
                casella.setY(y * CELL_SIZE);
                if (labirinto[y][x] == 1) {
                    casella.setFill(Color.BLACK);
                } else {
                    casella.setFill(Color.WHITE);
                }
                root.getChildren().add(casella);
            }
        }

        Rectangle arrivo = new Rectangle(CELL_SIZE, CELL_SIZE, Color.GREEN);
        arrivo.setX(xArrivo * CELL_SIZE);
        arrivo.setY(yArrivo * CELL_SIZE);
        root.getChildren().add(arrivo);
    }

    private void vittoria(Stage stage) {
        if (xGiocatore == xArrivo && yGiocatore == yArrivo) {
            tempo.setText("Hai vinto!");
        }
    }
    private void aggiornaTimer(){
    	if (xGiocatore == xArrivo && yGiocatore == yArrivo) {
    		tempo.setText("Hai vinto!");
    	}else if(Integer.parseInt(tempoRimasto+"")==0) {
    		tempo.setText("Hai perso");
    	}else {
    		tempo.setText("Tempo rimasto: "+ (tempoRimasto--));
    	}


    }



    public static void main(String[] args) {
        launch(args);
    }
}
