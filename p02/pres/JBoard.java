package p02.pres;

import p02.game.Board;
import p02.game.CellType;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class JBoard extends JPanel {
    private final Board board;
    private final Image backgroundImage;
    private final Image playerImage;
    private final Image playerWithPackageImage;
    private final Image packageIndicatorImage;
    private final Image turtleImage;
    private final Image fishImage;
    private final Image turtleDown;
    private final Image animatingPlayerImage;
    private final Image animatingPlayerWithPackageImage;

    private final SevenSegmentDigit digitHundreds = new SevenSegmentDigit();
    private final SevenSegmentDigit digitTens = new SevenSegmentDigit();
    private final SevenSegmentDigit digitUnits = new SevenSegmentDigit();

    public JBoard(Board board) {
        this.board = board;

        this.backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/background.png"))).getImage();
        this.playerImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/player.png"))).getImage();
        this.playerWithPackageImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/player_with_package.png"))).getImage();
        this.packageIndicatorImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/package_indicator.png"))).getImage();
        this.turtleImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/turtle.png"))).getImage();
        this.fishImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/fish.png"))).getImage();
        this.turtleDown = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/turtle_down.png"))).getImage();
        this.animatingPlayerImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/animating_player.png"))).getImage();
        this.animatingPlayerWithPackageImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/animating_player_package.png"))).getImage();

        setLayout(null);

        int digitWidth = 50;
        int digitHeight = 70;

        digitHundreds.setBounds(20, 20, digitWidth, digitHeight);
        digitTens.setBounds(20 + digitWidth + 5, 20, digitWidth, digitHeight);
        digitUnits.setBounds(20 + 2 * (digitWidth + 5), 20, digitWidth, digitHeight);

        digitHundreds.setOpaque(false);
        digitTens.setOpaque(false);
        digitUnits.setOpaque(false);

        add(digitHundreds);
        add(digitTens);
        add(digitUnits);
    }

    public void updateScore(int score) {
        digitHundreds.setValue((score / 100) % 10);
        digitTens.setValue((score / 10) % 10);
        digitUnits.setValue(score % 10);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        CellType[][] gameBoard = board.getBoard();
        int cellWidth = getWidth() / gameBoard[0].length;
        int cellHeight = getHeight() / gameBoard.length;

        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                switch (gameBoard[i][j]) {
                    case PLAYER -> g.drawImage(playerImage, j * cellWidth, i * cellHeight, cellWidth, cellHeight, this);
                    case PLAYER_WITH_PACKAGE -> g.drawImage(playerWithPackageImage, j * cellWidth, i * cellHeight, cellWidth, cellHeight, this);
                    case PACKAGE_INDICATOR -> {
                        int packageWidth = (int) (cellWidth * 1.8);
                        int packageHeight = (int) (cellHeight * 1.8);
                        int xOffset = (cellWidth - packageWidth) / 2;
                        int yOffset = (cellHeight - packageHeight) / 2;
                        g.drawImage(packageIndicatorImage, j * cellWidth + xOffset, i * cellHeight + yOffset, packageWidth, packageHeight, this);
                    }
                    case TURTLE -> {
                        int turtleWidth = (int) (cellWidth * 0.75);
                        int turtleHeight = (int) (cellHeight * 0.75);
                        int xOffset = (cellWidth - turtleWidth) / 2;
                        int yOffset = (cellHeight - turtleHeight) / 2;
                        g.drawImage(turtleImage, j * cellWidth + xOffset, i * cellHeight + yOffset, turtleWidth, turtleHeight, this);
                    }
                    case FISH -> {
                        int fishWidth = (int) (cellWidth * 0.38);
                        int fishHeight = (int) (cellHeight * 0.38);
                        int xOffset = (cellWidth - fishWidth) / 2;
                        int yOffset = (cellHeight - fishHeight) / 2;
                        g.drawImage(fishImage, j * cellWidth + xOffset, i * cellHeight + yOffset, fishWidth, fishHeight, this);
                    }
                    case TURTLE_DOWN -> {
                        int turtleDownWidth = (int) (cellWidth * 0.75);
                        int turtleDownHeight = (int) (cellHeight * 0.75);
                        int xOffset = (cellWidth - turtleDownWidth) / 2;
                        int yOffset = (cellHeight - turtleDownHeight) / 2;
                        g.drawImage(turtleDown, j * cellWidth + xOffset, i * cellHeight + yOffset, turtleDownWidth, turtleDownHeight, this);
                    }
                    case ANIMATING_PLAYER -> g.drawImage(animatingPlayerImage, j * cellWidth, i * cellHeight, cellWidth, cellHeight, this);
                    case ANIMATING_PLAYER_WITH_PACKAGE -> g.drawImage(animatingPlayerWithPackageImage, j * cellWidth, i * cellHeight, cellWidth, cellHeight, this);
                }
            }
        }
    }
}