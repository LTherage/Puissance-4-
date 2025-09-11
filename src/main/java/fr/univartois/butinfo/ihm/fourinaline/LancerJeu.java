package fr.univartois.butinfo.ihm.fourinaline;

import fr.univartois.butinfo.ihm.fourinaline.controller.HelloController;
import fr.univartois.butinfo.ihm.fourinaline.model.FacadeModele;
import fr.univartois.jfxnetwork.JFXNetworkClient;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;



public class LancerJeu extends HelloApplication{
    /**
     * Cette méthode permet d'initialiser l'affichage de la fenêtre de l'application.
     *
     * @param stage La fenêtre (initialement vide) de l'application.
     */
    private JFXNetworkClient jfx;
    private HelloController controller = new HelloController();

    @Override
    public void start(Stage stage) throws IOException {

        jfx = new JFXNetworkClient("192.168.245.105", 12345, controller);
        jfx.start();

        FacadeModele game = jfx.getModel(FacadeModele.class);
        controller.setGame(game);

    }

    /**
     * Cette méthode exécute l'application JavaFX.
     * Pour le cours d'IHM, la méthode {@code main} d'une application JavaFX sera
     * toujours la même : un simple appel à la méthode {@link #launch(String...)}
     * définie dans la classe {@link Application}.
     *
     * @param args Les arguments de la ligne de commande (dont on ne tient pas compte).
     *
     * @see #launch(String...)
     */
    public static void main(String[] args) {
        launch();
    }

}
