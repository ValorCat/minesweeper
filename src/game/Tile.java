package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.List;

public class Tile extends StackPane {

    private static final int TILE_SIZE = 32;
    private static final double MINE_SIZE = 0.9 * TILE_SIZE;
    private static final Image FLAG_IMG = new Image("flag.png", TILE_SIZE, TILE_SIZE, true, false);
    private static final Image MINE_IMG = new Image("mine.png", MINE_SIZE, MINE_SIZE, true, false);

    private boolean isMine, isRevealed, isFlagged;
    private final int row, col;
    private final Rectangle background;
    private final ImageView flag;

    public Tile(int row, int col) {
        this.row = row;
        this.col = col;
        background = new Rectangle(TILE_SIZE, TILE_SIZE);
        background.getStyleClass().addAll("tile", row % 2 == col % 2 ? "color-a" : "color-b");
        background.setOnMousePressed(this::click);
        flag = new ImageView(FLAG_IMG);
        flag.setOnMousePressed(this::click);
        flag.setVisible(false);
        getChildren().addAll(background, flag);
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    private void click(MouseEvent event) {
        MouseButton button = event.getButton();
        if (button == MouseButton.PRIMARY) {
            reveal();
        } else if (button == MouseButton.SECONDARY) {
            applyFlag();
        }
    }

    private void reveal() {
        if (isRevealed || isFlagged) return;
        Main.currentGame.checkFirstClick(row, col);
        isRevealed = true;
        background.getStyleClass().add("revealed");
        if (isMine) {
            getChildren().add(new ImageView(MINE_IMG));
        } else {
            // count adjacent mines and draw the number if > 0
            List<Tile> neighbors = Main.currentGame.getAdjacent(row, col);
            int number = getNumMines(neighbors);
            if (number != 0) {
                Text text = new Text(String.valueOf(number));
                text.getStyleClass().addAll("num", "n" + number);
                getChildren().add(text);
            } else {
                // if we're a 0, also recursively clear the nearby tiles
                for (Tile tile : neighbors) {
                    tile.reveal();
                }
            }
        }
    }

    private void applyFlag() {
        if (isRevealed) return;
        isFlagged = !isFlagged;
        flag.setVisible(isFlagged);
    }

    private int getNumMines(List<Tile> neighbors) {
        int numMines = 0;
        for (Tile adjacent : neighbors) {
            if (adjacent.isMine) {
                numMines++;
            }
        }
        return numMines;
    }

}
