package p02.game;

import p02.pres.SevenSegmentDigit;

import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Board implements KeyListener {

    private static Board instance;

    private final Random random = new Random();

    private final CellType[][] board = new CellType[5][12];
    private final List<ScoreListener> scoreListeners = new ArrayList<>();
    private final Set<Integer> divingTurtles = new HashSet<>();
    private final List<int[]> previousTurtlePositions = new ArrayList<>();
    private final List<int[]> previousFishPositions = new ArrayList<>();

    private boolean hasPackage = false;
    private boolean isAnimating = false;
    private boolean paused = false;
    private static boolean isGameRunning = false;
    private int score = 0;
    private int playerX = 0;
    private int playerY = 1;
    private int previousPlayerX = 0;
    private int previousPlayerY = 1;

    private SevenSegmentDigit ssdListener = new SevenSegmentDigit();
    private final SevenSegmentDigit hundredsDigit = new SevenSegmentDigit();
    private final SevenSegmentDigit tensDigit = new SevenSegmentDigit();
    private final SevenSegmentDigit onesDigit = new SevenSegmentDigit();

    private GameThread gameThread = new GameThread();

    private Board() {
        ssdListener = new SevenSegmentDigit();
        ssdListener.addStartEventListener(() -> System.out.println("StartEvent triggered!"));
        ssdListener.addPlusOneEventListener(() -> System.out.println("PlusOneEvent triggered!"));
        ssdListener.addResetEventListener(() -> System.out.println("ResetEvent triggered!"));

        initializeBoard();
        board[playerY][playerX] = CellType.PLAYER;
    }

    public static Board getInstance() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
    }

    //========================================

    // 1 - PLAYER
    public void moveLeft() {
        int targetX = playerX;
        for (int step = 0; step < 2; step++) {
            if (targetX > 0) {
                targetX--;
            }
        }
        if (playerY == 1 && targetX == 0 && !hasPackage) {
            System.out.println("Cannot enter without a package!");
            return;
        }

        int finalTargetX = targetX;
        animateMoveAsync(playerX, targetX, () -> {
            playerX = finalTargetX;
            updatePlayerPosition(playerX, playerY);
            checkPlayerInWater();
        });
    }

    public void moveRight() {
        int targetX = playerX;
        for (int step = 0; step < 2; step++) {
            if (targetX < board[0].length - 1) {
                targetX++;
            }
        }
        if (playerY == 1 && targetX == 11 && hasPackage) {
            System.out.println("Cannot enter with a package!");
            return;
        }

        if (playerY == 1 && targetX == 11 && board[0][11] != CellType.PACKAGE_INDICATOR) {
            System.out.println("No package to pick up – entry blocked.");
            return;
        }

        int finalTargetX = targetX;
        animateMoveAsync(playerX, targetX, () -> {
            playerX = finalTargetX;

            if (playerY == 1 && playerX == 11) {
                if (!hasPackage) {
                    hasPackage = true;
                    System.out.println("Player picked up the package!");

                    ssdListener.triggerPlusOneEvent();
                    addScore(random.nextInt(5) + 1);
                    updateSevenSegmentDigit(score);

                    playerX = 10;
                    updatePlayerPosition(playerX, playerY);

                    board[0][11] = CellType.NONE;
                } else {
                    System.out.println("You already have a package!");
                    playerX = 10;
                    updatePlayerPosition(playerX, playerY);
                }
                return;
            }

            updatePlayerPosition(playerX, playerY);
            System.out.println("Moved right to position: " + playerX + ", " + playerY);
            checkPlayerInWater();
        });
    }

    private void animateMoveAsync(int startX, int targetX, Runnable afterAnimation) {
        isAnimating = true;
        new Thread(() -> {
            int steps = Math.abs(targetX - startX);
            int direction = Integer.compare(targetX, startX);
            int currentX = startX;

            if (hasPackage) {
                board[playerY][currentX] = CellType.ANIMATING_PLAYER_WITH_PACKAGE;
            } else {
                board[playerY][currentX] = CellType.ANIMATING_PLAYER;
            }

            for (int i = 0; i < steps; i++) {
                board[playerY][currentX] = CellType.NONE;
                currentX += direction;

                if (hasPackage) {
                    board[playerY][currentX] = CellType.ANIMATING_PLAYER_WITH_PACKAGE;
                } else {
                    board[playerY][currentX] = CellType.ANIMATING_PLAYER;
                }

                try {
                    Thread.sleep(175);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            board[playerY][currentX] = CellType.NONE;

            printBoard(board);

            if (afterAnimation != null) {
                afterAnimation.run();
            }
            isAnimating = false;
        }).start();
    }

    public void updatePlayerPosition(int playerX, int playerY) {
        if (board[previousPlayerY][previousPlayerX] == CellType.PLAYER ||
                board[previousPlayerY][previousPlayerX] == CellType.PLAYER_WITH_PACKAGE) {
            board[previousPlayerY][previousPlayerX] = CellType.NONE;
        }

        // PICK UP PACKAGE
        if (playerY == 1 && playerX == 11 && !hasPackage) {
            hasPackage = true;
            addScore(random.nextInt(5) + 1);
            System.out.println("Points for picking up the package! Score: " + score);
            ssdListener.triggerPlusOneEvent();
            updateSevenSegmentDigit(score);
        }

        // DELIVERY PACKAGE
        if (playerY == 1 && playerX == 0 && hasPackage) {
            ssdListener.triggerPlusOneEvent();
            addScore(13 + random.nextInt(3));
            System.out.println("Points for delivery! Score: " + score);
            updateSevenSegmentDigit(score);
            hasPackage = false;
            if (score >= 999) {
                System.out.println("Congratulations! You reached 999 points. Game over!");
                endGame();
                return;
            }
            resetPlayerPosition();
            return;
        }

        if (hasPackage) {
            board[playerY][playerX] = CellType.PLAYER_WITH_PACKAGE;
        } else {
            board[playerY][playerX] = CellType.PLAYER;
        }

        previousPlayerX = playerX;
        previousPlayerY = playerY;
    }

    public void resetPlayerPosition() {
        ssdListener.triggerResetEvent();

        board[playerY][playerX] = CellType.NONE;

        playerX = 0;
        playerY = 1;
        board[playerY][playerX] = CellType.PLAYER;
    }

    // 2 - TURTLE
    public void generateTurtle() {
        for (int j = 1; j < board[2].length - 1; j++) {
            if (j % 2 == 0) {
                board[2][j] = CellType.TURTLE;
            }
        }
    }

    public boolean turtleDive() {
        boolean changed = false;
        previousTurtlePositions.clear();

        for (int j = 1; j < board[2].length - 1; j++) {
            if (board[2][j] == CellType.TURTLE && board[3][j] == CellType.FISH) {
                board[2][j] = CellType.TURTLE_DOWN;
                changed = true;
            } else if (board[2][j] == CellType.TURTLE_DOWN && board[3][j] == CellType.FISH) {
                previousTurtlePositions.add(new int[]{2, j});
                previousTurtlePositions.add(new int[]{3, j});

                board[2][j] = CellType.WATER;
                board[3][j] = CellType.TURTLE_DOWN;

                divingTurtles.add(j);
                System.out.println("Turtle at (2," + j + ") dived and caught fish at (3," + j + ")");
                changed = true;
            }
        }
        checkPlayerInWater();
        return changed;
    }

    public boolean turtleSurface() {
        boolean changed = false;
        for (int j : divingTurtles) {
            if (board[3][j] == CellType.TURTLE_DOWN) {
                previousTurtlePositions.add(new int[]{3, j});
                previousTurtlePositions.add(new int[]{2, j});
                board[2][j] = CellType.TURTLE;
                board[3][j] = CellType.NONE;
                System.out.println("Turtle at (3," + j + ") surfaced.");
                changed = true;
            }
        }
        divingTurtles.clear();
        return changed;
    }

    // 4 - WATER
    public void checkPlayerInWater() {
        if (playerY == 1 && board[2][playerX] == CellType.WATER) {
            System.out.println("Player has fallen into the water beneath.");
            if (hasPackage) {
                System.out.println("Player lost the package in the water.");
                hasPackage = false;
            }
            resetPlayerPosition();
            updatePlayerPosition(playerX, playerY);
            printBoard(board);
        }
    }

    // 5 - FISH
    public boolean generateFish() {
        boolean changed = false;
        updateSevenSegmentDigit(score);
        int maxFishCount = countNonZeroDigitsFromSevenSegment();
        int fishToSpawn = random.nextInt(maxFishCount + 1);

        int[] allowedColumns = {2, 4, 6, 8, 10};
        int currentFishCount = countFishOnBoard();

        while (fishToSpawn > 0 && currentFishCount < 3) {
            int col = allowedColumns[random.nextInt(allowedColumns.length)];
            if (board[4][col] == CellType.NONE) {
                board[4][col] = CellType.FISH;
                fishToSpawn--;
                currentFishCount++;
                changed = true;
            }
        }
        return changed;
    }

    private int countFishOnBoard() {
        int count = 0;
        for (CellType[] cellTypes : board) {
            for (CellType cellType : cellTypes) {
                if (cellType == CellType.FISH) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean moveFish() {
        System.out.println("Moving fish...");
        boolean changed = false;
        previousFishPositions.clear();

        for (int i = 0; i < board.length; i++) {
            for (int j = 1; j < board[i].length - 1; j++) {
                if (board[i][j] == CellType.FISH) {
                    int nextI = i - 1;
                    if (nextI >= 0 && board[nextI][j] == CellType.NONE) {
                        previousFishPositions.add(new int[]{i, j});
                        previousFishPositions.add(new int[]{nextI, j});
                        board[nextI][j] = CellType.FISH_MOVED;
                        board[i][j] = CellType.NONE;
                        System.out.println("Fish moved from " + i + "," + j + " to " + nextI + "," + j);
                        changed = true;
                    }
                }
            }
        }
        for (int[] pos : previousFishPositions) {
            int x = pos[1];
            int y = pos[0];
            if (board[y][x] == CellType.FISH_MOVED) {
                board[y][x] = CellType.FISH;
            }
        }

        return changed;
    }

    // 9 - PACKAGE INDICATOR

    public boolean generatePackageIndicator() {
        int randomValue = random.nextInt(2); // 0 lub 1
        if (randomValue == 1) {
            if (board[0][11] != CellType.PACKAGE_INDICATOR) {
                board[0][11] = CellType.PACKAGE_INDICATOR;
                System.out.println("Player can pick up a package!");
                return true;
            }
        } else {
            if (board[0][11] != CellType.NONE) {
                board[0][11] = CellType.NONE;
                System.out.println("Player cannot pick up a package at the moment.");
                return true;
            }
        }
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!isGameRunning || isAnimating || paused) {
            return;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            moveLeft();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_S) {
            if (!isGameRunning) {
                System.out.println("Game is starting...");
                gameThread = new GameThread();
                ssdListener.triggerStartEvent();
                gameThread.startGame(this);
                isGameRunning = true;
                paused = false;
            } else {
                paused = !paused;
                if (paused) {
                    System.out.println("Game is paused. Press 'S' to resume.");
                    gameThread.stopGame();
                } else {
                    System.out.println("Game is resuming...");
                    gameThread = new GameThread();
                    gameThread.startGame(this);
                }
            }
            printBoard(board);
        }
    }

    // ==========HELPERS!!!

    public void addScoreListener(ScoreListener listener) {
        scoreListeners.add(listener);
    }

    private void notifyScoreChanged() {
        for (ScoreListener listener : scoreListeners) {
            listener.onScoreChanged(score);
        }
    }

    public void addScore(int points) {
        score += points;
        notifyScoreChanged();
    }

    public void endGame() {
        isGameRunning = false;
        score = 0;
        if (gameThread != null) {
            gameThread.stopGame();
        }
        System.out.println("Game ended");
    }

    public static void printBoard(CellType[][] board) {
        if (!isGameRunning) {
            return;
        }
        for (CellType[] row : board) {
            for (CellType cell : row) {
                System.out.print(cell.getValue() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public CellType[][] getBoard() {
        return board;
    }

    private int countNonZeroDigitsFromSevenSegment() {
        int count = 0;

        if (hundredsDigit.getValue() != 0) count++;
        if (tensDigit.getValue() != 0) count++;
        if (onesDigit.getValue() != 0) count++;

        return count;
    }

    private void updateSevenSegmentDigit(int score) {
        int hundreds = (score / 100) % 10;
        int tens = (score / 10) % 10;
        int units = score % 10;

        hundredsDigit.setValue(hundreds);
        tensDigit.setValue(tens);
        onesDigit.setValue(units);
    }

    private void initializeBoard() {
        for (CellType[] cellTypes : board) {
            Arrays.fill(cellTypes, CellType.NONE);
        }
    }

}
