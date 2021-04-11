package game;

import javafx.scene.Group;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    private Tile[][] tiles;
    private int size, numMines;
    private boolean firstClick = true;

    public Game(Group root, int size, int numMines) {
        this.size = size;
        this.numMines = numMines;
        this.tiles = new Tile[size][size];
        GridPane grid = new GridPane();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Tile tile = new Tile(row, col);
                tiles[row][col] = tile;
                grid.add(tile, col, row);
            }
        }
//        placeMines(numMines, 0, 0);
        root.getChildren().add(grid);
    }

    public List<Tile> getAdjacent(int row, int col) {
        List<Tile> adjacent = new ArrayList<>(8);
        for (int adjRow = Math.max(0, row - 1); adjRow < Math.min(row + 2, size); adjRow++) {
            for (int adjCol = Math.max(0, col - 1); adjCol < Math.min(col + 2, size); adjCol++) {
                if (row != adjRow || col != adjCol) {
                    adjacent.add(tiles[adjRow][adjCol]);
                }
            }
        }
        return adjacent;
    }

    public void checkFirstClick(int row, int col) {
        if (firstClick) {
            firstClick = false;
            placeMines(numMines, row, col);
        }
    }

    private void placeMines(int numMines, int clickRow, int clickCol) {
        Random rng = new Random();
        List<Integer> choices = new ArrayList<>(size * size);
        for (int i = 0; i < size * size; i++) {
            choices.add(i);
        }

        // exclude tiles around first click
        int[] excluded = {
                (clickRow-1) * size + clickCol-1, // top left
                (clickRow-1) * size + clickCol,   // top middle
                (clickRow-1) * size + clickCol+1, // top right
                clickRow     * size + clickCol-1, // mid left
                clickRow     * size + clickCol,   // middle
                clickRow     * size + clickCol+1, // mid right
                (clickRow+1) * size + clickCol-1, // bottom left
                (clickRow+1) * size + clickCol,   // bottom middle
                (clickRow+1) * size + clickCol+1, // bottom right
        };
        for (int i = excluded.length - 1; i >= 0; i--) {
            choices.remove(excluded[i]);
        }

        for (int i = 0; i < numMines; i++) {
            int chosen = choices.remove(rng.nextInt(choices.size()));
            int col = chosen % size;
            int row = chosen / size;
            tiles[row][col].setMine(true);
        }
    }

}
