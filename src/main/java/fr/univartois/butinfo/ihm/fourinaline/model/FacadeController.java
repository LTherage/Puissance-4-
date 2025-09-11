package fr.univartois.butinfo.ihm.fourinaline.model;

public interface FacadeController {
    /**
     * Met à jour l'affichage après le placement d'un pion
     *
     * @param row la ligne où le pion a été placé
     * @param column la colonne où le pion a été placé
     * @param player le numéro du joueur (1 ou 2)
     */
    public abstract void updatePiecePlaced(int row, int column, int player);

    /**
     * Met à jour l'affichage pour le changement de joueur
     *
     * @param currentPlayer le numéro du joueur courant (1 ou 2)
     */
    public abstract void updateCurrentPlayer(int currentPlayer);

    /**
     * Notifie la fin de la partie
     *
     * @param winner le numéro du joueur gagnant (1 ou 2), 0 pour match nul
     */
    public abstract void updateGameOver(int winner);

    /**
     * Met à jour l'affichage après l'annulation d'un coup
     *
     * @param row la ligne où le pion a été retiré
     * @param column la colonne où le pion a été retiré
     */
    public abstract void updateMoveUndone(int row, int column);

    /**
     * Met à jour l'affichage après la réinitialisation du jeu
     */
    public abstract void updateGameReset();

    /**
     * Affiche un message d'erreur
     *
     * @param message le message d'erreur à afficher
     */
    public abstract void showError(String message);
    
}
