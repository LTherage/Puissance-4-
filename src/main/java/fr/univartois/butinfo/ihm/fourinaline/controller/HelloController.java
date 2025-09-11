/**
 * Ce logiciel est distribué à des fins éducatives.
 *
 * Il est fourni "tel quel", sans garantie d'aucune sorte, explicite
 * ou implicite, notamment sans garantie de qualité marchande, d'adéquation
 * à un usage particulier et d'absence de contrefaçon.
 * En aucun cas, les auteurs ou titulaires du droit d'auteur ne seront
 * responsables de tout dommage, réclamation ou autre responsabilité, que ce
 * soit dans le cadre d'un contrat, d'un délit ou autre, en provenance de,
 * consécutif à ou en relation avec le logiciel ou son utilisation, ou avec
 * d'autres éléments du logiciel.
 *
 * (c) 2022-2025 Romain Wallon - Université d'Artois.
 * Tous droits réservés.
 */

package fr.univartois.butinfo.ihm.fourinaline.controller;

import fr.univartois.butinfo.ihm.fourinaline.model.FacadeController;
import fr.univartois.butinfo.ihm.fourinaline.model.Grid;
import fr.univartois.butinfo.ihm.fourinaline.model.Token;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import fr.univartois.butinfo.ihm.fourinaline.model.FacadeModele;

/**
 * La classe HelloController illustre le fonctionnement du contrôleur associé à une vue.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public class HelloController implements FacadeController {

    /**
     * Le label affichant les messages du jeu
     */
    private Grid grid;
    private Token currentToken = Token.YELLOW;

    private FacadeController controller;

    @FXML
    private Label messageLabel;

    /**
     * La grille d'affichage du jeu
     */
    @FXML
    private GridPane gridPane;

    /**
     * Initialise le contrôleur
     */
    @FXML
    private void initialize() {

        grid = new Grid();
        currentToken = Token.YELLOW;

        for (int row = 0; row < Grid.ROWS; row++) {
            for (int col = 0; col < Grid.COLUMNS; col++) {
                StackPane cell = createCell();
                final int column = col;
                cell.setOnMouseClicked(event -> handleColumnClick(column));
                gridPane.add(cell, col, row);
            }
        }

        updateStatusMessage("Le joueur avec les jetons jaunes commence !");
    }

    /**
     * Crée une cellule de la grille
     */
    private StackPane createCell() {
        StackPane cell = new StackPane();
        cell.getStyleClass().add("game-cell");
        cell.setPrefSize(60, 60);


        ImageView imageView = new ImageView();
        imageView.setImage(new Image(getClass().getResourceAsStream("/images/EMPTY.gif")));
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        imageView.setPreserveRatio(true);
        imageView.getStyleClass().add("token-animation");

        cell.getChildren().add(imageView);
        return cell;
    }



    /**
     * Gère le clic sur une colonne
     */
    private void handleColumnClick(int column) {

        int row = playColumn(column);
        if (row >= 0) {

            Token tokenPlayed = currentToken.next();
            updateGrid(row, column, tokenPlayed);

            if (isGameOver()) {
                Token winner = getTokenAt(row, column);
                showGameOver(winner);
            } else {
                updateCurrentPlayer(currentToken);
            }
        } else {
            showError("Cette colonne est pleine !");
        }
    }


    public int playColumn(int column) {
        int row = grid.play(currentToken, column);
        if (row >= 0) {

            currentToken = currentToken.next();
        }
        return row;
    }

    /**
     * Récupère le jeton du joueur courant
     *
     * @return le jeton du joueur courant
     */
    public Token getCurrentToken() {
        return currentToken;
    }

    /**
     * Récupère le jeton à une position donnée
     *
     * @param row la ligne
     * @param column la colonne
     * @return le jeton à la position spécifiée
     */
    public Token getTokenAt(int row, int column) {
        return grid.get(row, column);
    }



    public void updateGrid(int row, int column, Token token) {
        StackPane cell = (StackPane) getNodeFromGridPane(row, column);
        ImageView imageView = (ImageView) cell.getChildren().get(0);

        String imagePath = switch (token) {
            case YELLOW -> "/images/YELLOW.gif";
            case RED -> "/images/RED.gif";
            default -> "/images/EMPTY.gif";
        };

        imageView.setImage(new Image(getClass().getResourceAsStream(imagePath)));
    }

    @Override
    public void updateCurrentPlayer(int currentPlayer){
        String player = (currentPlayer == 1) ? "jaunes" : "rouges";
        updateStatusMessage("C'est au tour du joueur avec les jetons " + player);
    }

    @Override
    public void updateMoveUndone(int row, int column) {
        StackPane cell = (StackPane) getNodeFromGridPane(row, column);
        ImageView imageView = (ImageView) cell.getChildren().get(0);
        imageView.setImage(new Image(getClass().getResourceAsStream("/images/EMPTY.gif")));
        updateStatusMessage("Le coup a été annulé.");
    }


    @Override
    public void updateGameOver(int winner){
        if (winner > 0) {
            String player = (winner == 1) ? "jaunes" : "rouges";
            updateStatusMessage("Le joueur avec les jetons " + player + " a gagné !");
        } else {
            updateStatusMessage("Match nul !");
        }

        gridPane.setDisable(true);

    }


    public void updateCurrentPlayer(Token currentToken) {
        String player = (currentToken == Token.YELLOW) ? "jaunes" : "rouges";
        updateStatusMessage("C'est au tour du joueur avec les jetons " + player);
    }

    public boolean isGameOver() {
        return grid.findFourInALine().isPresent() || grid.isFull();
    }

    /**
     * Réinitialise la partie
     */
    public void resetGame() {
        grid.clear();
        currentToken = Token.YELLOW;
    }




    public void showGameOver(Token winner) {
        if (winner != null) {
            String player = (winner == Token.YELLOW) ? "jaunes" : "rouges";
            updateStatusMessage("Le joueur avec les jetons " + player + " a gagné !");
        } else {
            updateStatusMessage("Match nul !");
        }

        gridPane.setDisable(true);
    }

    @Override
    public void showError(String message) {
        updateStatusMessage("Erreur : " + message);
    }

    @Override
    public void updatePiecePlaced(int row, int column, int player) {
        StackPane cell = (StackPane) getNodeFromGridPane(row, column);
        ImageView imageView = (ImageView) cell.getChildren().get(0);

        String imagePath = (player == 1) ? "/images/YELLOW.gif" : "/images/RED.gif";
        imageView.setImage(new Image(getClass().getResourceAsStream(imagePath)));


        String nextPlayer = (player == 1) ? "rouges" : "jaunes";
        updateStatusMessage("C'est au tour du joueur avec les jetons " + nextPlayer);
    }


    @Override
    public void updateGameReset() {

        for (int row = 0; row < Grid.ROWS; row++) {
            for (int col = 0; col < Grid.COLUMNS; col++) {
                StackPane cell = (StackPane) getNodeFromGridPane(row, col);
                ImageView imageView = (ImageView) cell.getChildren().get(0);
                imageView.setImage(new Image(getClass().getResourceAsStream("/images/EMPTY.gif")));
            }
        }
        gridPane.setDisable(false);
        updateStatusMessage("Nouvelle partie ! Le joueur avec les jetons jaunes commence.");
    }



    public void updateStatusMessage(String message) {
        messageLabel.setText(message);
    }

    /**
     * Récupère un nœud de la grille à une position donnée
     */
    private javafx.scene.Node getNodeFromGridPane(int row, int col) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return node;
            }
        }
        return null;
    }


    /**
     * Gestionnaire pour le bouton "Nouvelle Partie"
     */
    @FXML
    private void handleNewGame() {

        resetGame();
        resetDisplay();
    }

    public void setController(FacadeController controller) {
        this.controller = controller;
    }

    public void setGame(FacadeModele game) {
        this.grid = game.getGrid();
        this.currentToken = game.getActiveToken();

    }


    private void resetDisplay() {

        for (int row = 0; row < Grid.ROWS; row++) {
            for (int col = 0; col < Grid.COLUMNS; col++) {
                StackPane cell = (StackPane) getNodeFromGridPane(row, col);
                ImageView imageView = (ImageView) cell.getChildren().get(0);
                imageView.setImage(new Image(getClass().getResourceAsStream("/images/EMPTY.gif")));
            }
        }


        gridPane.setDisable(false);


        updateStatusMessage("Nouvelle partie ! Le joueur avec les jetons jaunes commence.");
    }


}
