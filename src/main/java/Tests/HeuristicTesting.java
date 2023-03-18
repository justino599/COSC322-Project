package Tests;

import State.BitBoard;
import Tree.Heuristics;

public class HeuristicTesting {
    public static void main(String[] args) {
        BitBoard board = new BitBoard();
        board.setPiece(6, 0, 1);
        System.out.println(Heuristics.calculateTileControl(2, 0, board));
    }
}
