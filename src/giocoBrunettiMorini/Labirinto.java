package giocoBrunettiMorini;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Labirinto extends Application {
    private static final int CELL_SIZE = 40;
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;
    private static final int TIME_LIMIT = 30; // secondi 

    private Rectangle giocatore;
    private int xGiocatore = 1;
    private int yGiocatore = 1;

    private int xArrivo = 8;
    private int yArrivo = 8;

    private Timeline timer;
    private int tempoRimasto = TIME_LIMIT;
    private Text tempo;
    
    //Image muro = new Image(getClass().getResourceAsStream("muro.jpg"));

    // 1 = muro, 0 = strada
    private final int[][] labirinto = {
            {1,1,1,1,1,1,1,1,1,1},
            {1,0,0,1,0,0,0,0,0,1},
            {1,0,0,1,0,1,1,1,0,1},
            {1,0,1,1,0,1,0,0,0,1},
            {1,0,0,0,0,1,0,1,0,1},
            {1,0,1,1,1,1,0,1,0,1},
            {1,0,0,0,0,0,0,1,0,1},
            {1,1,1,1,1,1,0,1,0,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1}
    };

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE + 40);
        tempo = new Text("Tempo: " + tempoRimasto);
        tempo.setFont(Font.font(20));
        tempo.setY(HEIGHT * CELL_SIZE + 30);
        root.getChildren().add(tempo);

        caricaLabirinto(root);

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

        // startTimer(stage);
        caricaLabirinto(root);
        root.getChildren().add(giocatore);

        stage.setScene(scene);
        stage.setTitle("Labirinto - JavaFX");
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

    public static void main(String[] args) {
        launch(args);
    }
}


