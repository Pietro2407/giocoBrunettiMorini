package giocoBrunettiMorini;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Labirinto extends Application {
    private static final int CELL_SIZE = 32;
    private static final int WIDTH = 30;
    private static final int HEIGHT = 27;
    private static final int TIME_LIMIT = 45;

    private Stage stage;
    private Scene menuScene;

    private ImageView giocatore;
    private int xGiocatore = 1;
    private int yGiocatore = 1;
    private int xArrivo = 28;
    private int yArrivo = 25;
    private ImageView immagineFinale;

    private ImageView nemico1, nemico2;
    private int xNemico1 = 5, yNemico1 = 5;
    private int xNemico2 = 10, yNemico2 = 20;
    private int dxNemico1 = 1, dyNemico1 = 0;
    private int dxNemico2 = 0, dyNemico2 = 1;

    private int tempoRimasto = TIME_LIMIT;
    private Text tempo;

    private int[][] labirinto;

    private final int[][] livello1 = {
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

    private final int[][] livello2 = {
    		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
    	    {1,0,0,1,0,0,0,1,0,1,0,0,0,1,0,1,0,0,0,0,0,1,0,0,0,1,0,1,0,1},
    	    {1,0,1,1,0,1,0,1,0,1,1,1,0,1,0,1,0,1,1,1,0,1,1,1,0,1,0,1,0,1},
    	    {1,0,1,0,0,1,0,0,0,0,0,1,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,1,0,1},
    	    {1,0,1,0,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,0,1,1,1,1,1,0,1},
    	    {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,1,0,1},
    	    {1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,0,1,1,1,1,1,0,1},
    	    {1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,1},
    	    {1,0,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1},
    	    {1,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1},
    	    {1,0,1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1},
    	    {1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,1},
    	    {1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1},
    	    {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1},
    	    {1,0,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,0,1},
    	    {1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,1},
    	    {1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1},
    	    {1,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1},
    	    {1,0,1,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1},
    	    {1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1},
    	    {1,0,1,0,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1},
    	    {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,1},
    	    {1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,0,1},
    	    {1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1},
    	    {1,0,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,0,1},
    	    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
    	    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
};


    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        VBox menu = new VBox(10);
        menu.setPrefSize(WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE);
        menu.setStyle("-fx-alignment: center; -fx-padding: 100");

        Button livello1Btn = new Button("Livello 1");
        livello1Btn.setOnAction(e -> avviaGioco(livello1));
        Button livello2Btn = new Button("Livello 2");
        livello2Btn.setOnAction(e -> avviaGioco(livello2));

        menu.getChildren().addAll(new Text("Seleziona il livello:"), livello1Btn, livello2Btn);
        menuScene = new Scene(menu);
        stage.setScene(menuScene);
        stage.setTitle("Selezione Livello");
        stage.show();
    }

    private void avviaGioco(int[][] lab) {
        labirinto = lab;
        xGiocatore = 1;
        yGiocatore = 1;
        xArrivo = 28;
        yArrivo = 25;
        tempoRimasto = TIME_LIMIT;

        Pane pannello = new Pane();
        Scene scene = new Scene(pannello, WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE + 60);

        tempo = new Text("Tempo rimasto: " + tempoRimasto);
        tempo.setFont(Font.font(20));
        tempo.setY(HEIGHT * CELL_SIZE + 30);
        pannello.getChildren().add(tempo);

        caricaLabirinto(pannello);

        Image imgGiocatore = new Image(getClass().getResourceAsStream("immagini/tile6.png"));
        giocatore = new ImageView(imgGiocatore);
        giocatore.setFitWidth(CELL_SIZE);
        giocatore.setFitHeight(CELL_SIZE);
        pannello.getChildren().add(giocatore);

        // Nemico 1
        nemico1 = new ImageView(new Image(getClass().getResourceAsStream("immagini/nemico1.png")));
        nemico1.setFitWidth(CELL_SIZE);
        nemico1.setFitHeight(CELL_SIZE);
        pannello.getChildren().add(nemico1);

        // Nemico 2
        nemico2 = new ImageView(new Image(getClass().getResourceAsStream("immagini/nemico2.png")));
        nemico2.setFitWidth(CELL_SIZE);
        nemico2.setFitHeight(CELL_SIZE);
        pannello.getChildren().add(nemico2);

        aggiornaPosizioni();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            aggiornaTimer();
            muoviNemici();
            controllaCollisioni();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        scene.setOnKeyPressed(e -> {
            int dx = 0, dy = 0;
            if (e.getCode() == KeyCode.UP) dy = -1;
            else if (e.getCode() == KeyCode.DOWN) dy = 1;
            else if (e.getCode() == KeyCode.LEFT) dx = -1;
            else if (e.getCode() == KeyCode.RIGHT) dx = 1;

            int newX = xGiocatore + dx;
            int newY = yGiocatore + dy;

            if (newX >= 0 && newX < WIDTH && newY >= 0 && newY < HEIGHT && labirinto[newY][newX] == 0) {
                xGiocatore = newX;
                yGiocatore = newY;
                aggiornaPosizioni();
                controllaCollisioni();
                if (xGiocatore == xArrivo && yGiocatore == yArrivo) {
                    mostraImmagineFinale("immagini/hai_vinto.png");
                }
            }
        });

        stage.setScene(scene);
    }

    private void caricaLabirinto(Pane root) {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                String imgPath = labirinto[y][x] == 1 ? "immagini/pavimento.png" : "immagini/muro.png";
                ImageView tile = new ImageView(new Image(getClass().getResourceAsStream(imgPath)));
                tile.setX(x * CELL_SIZE);
                tile.setY(y * CELL_SIZE);
                root.getChildren().add(tile);
            }
        }
        ImageView arrivo = new ImageView(new Image(getClass().getResourceAsStream("immagini/arrivo.png")));
        arrivo.setX(xArrivo * CELL_SIZE);
        arrivo.setY(yArrivo * CELL_SIZE);
        root.getChildren().add(arrivo);
    }

    private void aggiornaTimer() {
        if (tempoRimasto <= 0) {
            mostraImmagineFinale("immagini/hai_perso.png");
        } else {
            tempo.setText("Tempo rimasto: " + (--tempoRimasto));
        }
    }

    private void aggiornaPosizioni() {
        giocatore.setX(xGiocatore * CELL_SIZE);
        giocatore.setY(yGiocatore * CELL_SIZE);
        nemico1.setX(xNemico1 * CELL_SIZE);
        nemico1.setY(yNemico1 * CELL_SIZE);
        nemico2.setX(xNemico2 * CELL_SIZE);
        nemico2.setY(yNemico2 * CELL_SIZE);
    }

    private void muoviNemici() {
        muoviNemico1();
        muoviNemico2();
        aggiornaPosizioni();
    }

    private void muoviNemico1() {
        int nextX = xNemico1 + dxNemico1;
        int nextY = yNemico1 + dyNemico1;
        if (labirinto[nextY][nextX] == 0) {
            xNemico1 = nextX;
            yNemico1 = nextY;
        } else {
            dxNemico1 *= -1;
            dyNemico1 *= -1;
        }
    }

    private void muoviNemico2() {
        int nextX = xNemico2 + dxNemico2;
        int nextY = yNemico2 + dyNemico2;
        if (labirinto[nextY][nextX] == 0) {
            xNemico2 = nextX;
            yNemico2 = nextY;
        } else {
            dxNemico2 *= -1;
            dyNemico2 *= -1;
        }
    }

    private void controllaCollisioni() {
        if ((xGiocatore == xNemico1 && yGiocatore == yNemico1) ||
            (xGiocatore == xNemico2 && yGiocatore == yNemico2)) {
            mostraImmagineFinale("immagini/hai_perso.png");
        }
    }

    private void mostraImmagineFinale(String percorsoImmagine) {
        Image img = new Image(getClass().getResourceAsStream(percorsoImmagine));
        immagineFinale = new ImageView(img);
        immagineFinale.setFitWidth(WIDTH * CELL_SIZE);
        immagineFinale.setFitHeight(HEIGHT * CELL_SIZE);
        immagineFinale.setX(0);
        immagineFinale.setY(0);
        ((Pane) giocatore.getParent()).getChildren().add(immagineFinale);
        giocatore.setVisible(false);
        nemico1.setVisible(false);
        nemico2.setVisible(false);
        tempo.setVisible(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
