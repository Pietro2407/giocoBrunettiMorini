package giocoBrunettiMorini;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.control.ScrollPane;

public class Labirinto extends Application {
    private static final int CELL_SIZE = 20;
    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;

    private static final int TIME_LIMIT = 30; // secondi 

    private Rectangle giocatore;
    private int xGiocatore = 1;
    private int yGiocatore = 1;

    private int xArrivo = 28;
    private int yArrivo = 28;

    private Timeline timer;
    private int tempoRimasto = TIME_LIMIT;
    private Text tempo;

    // 1 = muro, 0 = strada
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
        {1,0,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1},
        {1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1},
        {1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1},
        {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,1},
        {1,0,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1},
        {1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1},
        {1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,0,1}
    };

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setPrefSize(WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        Scene scene = new Scene(scrollPane, 600, 600); // Dimensione finestra visibile

        tempo = new Text("Tempo: " + tempoRimasto);
        tempo.setFont(Font.font(20));
        tempo.setY(HEIGHT * CELL_SIZE + 30);
        root.getChildren().add(tempo);

        caricaLabirinto(root);

        giocatore = new Rectangle(CELL_SIZE, CELL_SIZE, Color.BLUE);
        giocatore.setX(xGiocatore * CELL_SIZE);
        giocatore.setY(yGiocatore * CELL_SIZE);
        root.getChildren().add(giocatore);

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

                // Muove la visuale
                double centerX = giocatore.getX() + CELL_SIZE / 2.0;
                double centerY = giocatore.getY() + CELL_SIZE / 2.0;

                double hValue = (centerX - scene.getWidth() / 2.0) / (WIDTH * CELL_SIZE - scene.getWidth());
                double vValue = (centerY - scene.getHeight() / 2.0) / (HEIGHT * CELL_SIZE - scene.getHeight());

                scrollPane.setHvalue(Math.max(0, Math.min(1, hValue)));
                scrollPane.setVvalue(Math.max(0, Math.min(1, vValue)));

                vittoria(stage);
            }
        });

        stage.setScene(scene);
        stage.setTitle("Labirinto - JavaFX con camera");
        stage.show();

        // Centra la visuale all'inizio
        Platform.runLater(() -> {
            double centerX = giocatore.getX() + CELL_SIZE / 2.0;
            double centerY = giocatore.getY() + CELL_SIZE / 2.0;

            double hValue = (centerX - scene.getWidth() / 2.0) / (WIDTH * CELL_SIZE - scene.getWidth());
            double vValue = (centerY - scene.getHeight() / 2.0) / (HEIGHT * CELL_SIZE - scene.getHeight());

            scrollPane.setHvalue(Math.max(0, Math.min(1, hValue)));
            scrollPane.setVvalue(Math.max(0, Math.min(1, vValue)));
        });
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
