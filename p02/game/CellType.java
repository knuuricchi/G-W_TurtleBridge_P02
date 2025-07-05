package p02.game;

public enum CellType {
    NONE(0),
    PLAYER(1),
    ANIMATING_PLAYER(11),
    TURTLE(2),
    WATER(4),
    FISH(5),
    PLAYER_WITH_PACKAGE(6),
    ANIMATING_PLAYER_WITH_PACKAGE(66),
    FISH_MOVED(7),
    PACKAGE_INDICATOR(9),
    TURTLE_DOWN(8);

    private final int value;

    CellType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
