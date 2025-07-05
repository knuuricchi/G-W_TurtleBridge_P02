import javax.swing.SwingUtilities;
import p02.game.Board;
import p02.pres.GameFrame;

public class Main {
    public static void main(String[] args) {
        Board gameBoard = Board.getInstance();

        SwingUtilities.invokeLater(() -> {
            new GameFrame(gameBoard);
        });

        gameBoard.generateTurtle();
        gameBoard.generateFish();
        Board.printBoard(gameBoard.getBoard());
    }
}