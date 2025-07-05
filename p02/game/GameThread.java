package p02.game;

public class GameThread extends Thread {
    private boolean running = false;
    private Board board;

    private int tickDelay = 2900;
    private final int difficultyInterval = 2;
    private final int minTickDelay = 500;

    public void startGame(Board board) {
        this.board = board;
        running = true;
        start();
    }

    public void stopGame() {
        running = false;
    }

    @Override
    public void run() {
        int tickCount = 0;
        while (running) {
            try {
                boolean boardChanged = false;

                board.checkPlayerInWater();

                if (board.generatePackageIndicator()) {
                    boardChanged = true;
                }
                if (board.turtleSurface()) {
                    boardChanged = true;
                }
                if (board.turtleDive()) {
                    boardChanged = true;
                }
                if (board.moveFish()) {
                    boardChanged = true;
                }
                if (board.generateFish()) {
                    boardChanged = true;
                }

                if (boardChanged) {
                    Board.printBoard(board.getBoard());
                }

                tickCount++;
                if (tickCount % difficultyInterval == 0 && tickDelay > minTickDelay) {
                    tickDelay -= 29;
                    System.out.println("Increased difficulty! New tick delay: " + tickDelay + "ms");
                }

                Thread.sleep(tickDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}