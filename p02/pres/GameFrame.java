package p02.pres;

import p02.game.Board;
import p02.game.ScoreListener;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame implements ScoreListener {

    private final JBoard jBoard;

    public GameFrame(Board board) {
        setTitle("G&W Turtle Bridge");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        board.addScoreListener(this);

        jBoard = new JBoard(board);
        add(jBoard, BorderLayout.CENTER);

        addKeyListener(board);

        new Timer(200, e -> jBoard.repaint()).start();

        setVisible(true);
    }

    @Override
    public void onScoreChanged(int newScore) {
        jBoard.updateScore(newScore);
    }
}