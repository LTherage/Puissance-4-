package fr.univartois.butinfo.ihm.fourinaline;

import fr.univartois.butinfo.ihm.fourinaline.model.FacadeController;
import fr.univartois.butinfo.ihm.fourinaline.model.FacadeModele;
import fr.univartois.butinfo.ihm.fourinaline.model.Grid;
import fr.univartois.butinfo.ihm.fourinaline.model.Token;


public class ClassiqueModele implements FacadeModele {
    private Grid grid;
    private Token activeToken;
    private FacadeController controller;

    public ClassiqueModele() {
        this.grid = new Grid();
        this.activeToken = Token.YELLOW; // Jeton initial
    }

    @Override
    public Grid getGrid() {
        return grid;
    }

    @Override
    public Token getActiveToken() {
        return activeToken;
    }

    @Override
    public void placeToken(int column) {

        if (!grid.isColumnFull(column)) {
            grid.addToken(column, activeToken);
            activeToken = (activeToken == Token.YELLOW) ? Token.RED : Token.YELLOW;
        }
    }

    @Override
    public boolean hasWinner() {
        return grid.findFourInALine().isPresent();
    }

    @Override
    public void setController(FacadeController controller) {
        this.controller = controller;
    }

    @Override
    public void initialize() {
        grid.clear();
        activeToken = Token.YELLOW;
    }
    public FacadeController getController() {
        return controller;
    }


}