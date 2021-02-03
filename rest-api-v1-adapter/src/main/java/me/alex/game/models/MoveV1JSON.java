package me.alex.game.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class MoveV1JSON {

    @Min(0)
    @Max(2)
    private final int x;
    @Min(0)
    @Max(2)
    private final int y;
    private final Symbol symbol;


    MoveV1JSON() {
        this(0, 0, Symbol.X);
    }

    public MoveV1JSON(int x, int y, Symbol symbol) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
    }


    public Symbol getSymbol() {
        return symbol;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
