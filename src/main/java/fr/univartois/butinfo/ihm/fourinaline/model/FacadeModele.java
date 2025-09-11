package fr.univartois.butinfo.ihm.fourinaline.model;

public interface FacadeModele {
    /**
     * Retourne la grille du jeu.
     *
     * @return La grille du jeu.
     */
    public  Grid getGrid();

    /**
     * Retourne le jeton actuellement actif (celui qui doit jouer).
     *
     * @return Le jeton actif.
     */
    public abstract Token getActiveToken();

    /**
     * Place un jeton dans la colonne spécifiée.
     *
     * @param column La colonne où placer le jeton.
     */
    public void placeToken(int column);

    /**
     * Vérifie si un joueur a gagné.
     *
     * @return {@code true} si un joueur a gagné, {@code false} sinon.
     */
    public boolean hasWinner();

    void setController(FacadeController controller);

    void initialize();
}
