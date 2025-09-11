package fr.univartois.butinfo.ihm.fourinaline;


import fr.univartois.butinfo.ihm.fourinaline.model.FacadeController;
import fr.univartois.butinfo.ihm.fourinaline.model.FacadeModele;
import fr.univartois.jfxnetwork.JFXNetworkServer;

import java.io.IOException;


public class Classique {
    public static void main(String[] args) throws IOException {
        FacadeModele game = new ClassiqueModele();
        try (JFXNetworkServer<FacadeController> server =
                     new JFXNetworkServer<>(12345, game, FacadeController.class)) {
            FacadeController controller = server.getController();
            game.setController(controller);
            server.start(2, game::initialize);

        }
    }
}
