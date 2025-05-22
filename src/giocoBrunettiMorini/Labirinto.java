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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Labirinto extends Application {
    private static final int CELL_SIZE = 32;
    private static final int WIDTH = 30;
    private static final int HEIGHT = 27;
    private static final int TIME_LIMIT = 60;

    private ImageView giocatore;
    private int xGiocatore = 1;
    private int yGiocatore = 1;
    private int xArrivo = 28;
    private int yArrivo = 25;
    private ImageView immagineFinale;

    private ImageView nemico1;
    private int xNemico1 = 5, yNemico1 = 5;


    private ImageView nemico2;
    private int xNemico2 = 16, yNemico2 = 20;


    private int tempoRimasto = TIME_LIMIT;
    private Text tempo;

    private final int[][] labirinto = {
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
    	    {1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,0,1},
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
    public void start(Stage stage) {
        Pane pannello = new Pane();
        Scene scene = new Scene(pannello, WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE + 60);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), x -> aggiornaTimer()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        tempo = new Text("Tempo rimasto: " + tempoRimasto);
        tempo.setFont(Font.font(20));
        tempo.setY(HEIGHT * CELL_SIZE + 30);
        pannello.getChildren().add(tempo);

        caricaLabirinto(pannello);

        Image imgGiocatore = new Image(getClass().getResourceAsStream("immagini/tile6.png"));
        giocatore = new ImageView(imgGiocatore);
        giocatore.setFitWidth(CELL_SIZE);
        giocatore.setFitHeight(CELL_SIZE);
        giocatore.setX(xGiocatore * CELL_SIZE);
        giocatore.setY(yGiocatore * CELL_SIZE);
        pannello.getChildren().add(giocatore);

       
        Image imgNemico1 = new Image(getClass().getResourceAsStream("immagini/nemico1.png"));
        nemico1 = new ImageView(imgNemico1);
        nemico1.setFitWidth(CELL_SIZE);
        nemico1.setFitHeight(CELL_SIZE);
        nemico1.setX(xNemico1 * CELL_SIZE);
        nemico1.setY(yNemico1 * CELL_SIZE);
        pannello.getChildren().add(nemico1);

        
        Image imgNemico2 = new Image(getClass().getResourceAsStream("immagini/nemico2.png"));
        nemico2 = new ImageView(imgNemico2);
        nemico2.setFitWidth(CELL_SIZE);
        nemico2.setFitHeight(CELL_SIZE);
        nemico2.setX(xNemico2 * CELL_SIZE);
        nemico2.setY(yNemico2 * CELL_SIZE);
        pannello.getChildren().add(nemico2);

        Timeline movimentoNemici = new Timeline(new KeyFrame(Duration.millis(500), e -> muoviNemici()));
        movimentoNemici.setCycleCount(Timeline.INDEFINITE);
        movimentoNemici.play();

        final String[] direzione = {"giu"};

        scene.setOnKeyPressed(e -> {
            int dx = 0, dy = 0;

            if (e.getCode() == KeyCode.UP) {
                dy = -1;
                direzione[0] = "su";
            } else if (e.getCode() == KeyCode.DOWN) {
                dy = 1;
                direzione[0] = "giu";
            } else if (e.getCode() == KeyCode.LEFT) {
                dx = -1;
                direzione[0] = "sinistra";
            } else if (e.getCode() == KeyCode.RIGHT) {
                dx = 1;
                direzione[0] = "destra";
            }

            int newX = xGiocatore + dx;
            int newY = yGiocatore + dy;

            if (newX >= 0 && newX < WIDTH && newY >= 0 && newY < HEIGHT && labirinto[newY][newX] == 0) {
                xGiocatore = newX;
                yGiocatore = newY;
                giocatore.setX(xGiocatore * CELL_SIZE);
                giocatore.setY(yGiocatore * CELL_SIZE);

                int indicatore = switch (direzione[0]) {
                    case "destra" -> 6;
                    case "sinistra" -> 2;
                    case "su" -> 0;
                    case "giu" -> 4;
                    default -> 6;
                };
                String nomeImmagine = "tile" + (indicatore) + ".png";
                Image nuovaImg = new Image(getClass().getResourceAsStream("immagini/" + nomeImmagine));
                giocatore.setImage(nuovaImg);

                vittoria();
            }
        });
        

        stage.setScene(scene);
        stage.setTitle("Labirinto");
        stage.show();
    }

    private void muoviNemici() {
        muoviNemicoCasuale(nemico1, new int[]{xNemico1, yNemico1}, nemico -> {
            xNemico1 = nemico[0];
            yNemico1 = nemico[1];
        });

        muoviNemicoCasuale(nemico2, new int[]{xNemico2, yNemico2}, nemico -> {
            xNemico2 = nemico[0];
            yNemico2 = nemico[1];
        });

        controllaCollisioni();
    }
    
    private void muoviNemicoCasuale(ImageView nemico, int[] posizione, java.util.function.Consumer<int[]> aggiornaPosizione) {
        int x = posizione[0];
        int y = posizione[1];

        int[][] direzioni = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        java.util.List<int[]> direzioniValide = new java.util.ArrayList<>();

        for (int[] d : direzioni) {
            int newX = x + d[0];
            int newY = y + d[1];
            if (newX >= 0 && newX < WIDTH && newY >= 0 && newY < HEIGHT && labirinto[newY][newX] == 0) {
                direzioniValide.add(d);
            }
        }

        if (!direzioniValide.isEmpty()) {
            int[] scelta = direzioniValide.get((int) (Math.random() * direzioniValide.size()));
            int nuovoX = x + scelta[0];
            int nuovoY = y + scelta[1];

            aggiornaPosizione.accept(new int[]{nuovoX, nuovoY});
            nemico.setX(nuovoX * CELL_SIZE);
            nemico.setY(nuovoY * CELL_SIZE);
        }
    }


    private void controllaCollisioni() {
        if ((xGiocatore == xNemico1 && yGiocatore == yNemico1) ||
            (xGiocatore == xNemico2 && yGiocatore == yNemico2)) {
            mostraImmagineFinale("immagini/hai_perso.png");
        }
    }



    private void caricaLabirinto(Pane root) {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Image img = new Image(getClass().getResourceAsStream("immagini/" + (labirinto[y][x] == 1 ? "pavimento" : "muro") + ".png"));
                ImageView cella = new ImageView(img);
                cella.setX(x * CELL_SIZE);
                cella.setY(y * CELL_SIZE);
                root.getChildren().add(cella);
            }
        }

        Image fotoArrivo = new Image(getClass().getResourceAsStream("immagini/arrivo.png"));
        ImageView arrivo = new ImageView(fotoArrivo);
        arrivo.setX(xArrivo * CELL_SIZE);
        arrivo.setY(yArrivo * CELL_SIZE);
        root.getChildren().add(arrivo);
    }

    private void vittoria() {
        if (xGiocatore == xArrivo && yGiocatore == yArrivo) {
            mostraImmagineFinale("immagini/hai_vinto.png");
            
        }
    }

    private void aggiornaTimer() {
        if (xGiocatore == xArrivo && yGiocatore == yArrivo) return;

        if (tempoRimasto <= 0) {
            mostraImmagineFinale("immagini/hai_perso.png");
        } else {
            tempo.setText("Tempo rimasto: " + (--tempoRimasto));
        }
    }

    private void mostraImmagineFinale(String percorsoImmagine) {
        Image imgFinale = new Image(getClass().getResourceAsStream(percorsoImmagine));
        immagineFinale = new ImageView(imgFinale);
        immagineFinale.setFitWidth(WIDTH * CELL_SIZE);
        immagineFinale.setFitHeight(HEIGHT * CELL_SIZE);
        immagineFinale.setX(0);
        immagineFinale.setY(0);
        Button prossimoLivello = new Button("Prossimo livello");
        prossimoLivello.setLayoutY(HEIGHT * CELL_SIZE + 30);
        prossimoLivello.setLayoutX(HEIGHT * CELL_SIZE -60);

        giocatore.setVisible(false);
        tempo.setVisible(false);

        ((Pane) giocatore.getParent()).getChildren().add(immagineFinale);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
