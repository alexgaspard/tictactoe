package me.alex.game.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class MoveV2JSON {

    @Min(0)
    @Max(8)
    private final int i;
    private final Symbol symbol;


    MoveV2JSON() {
        this(0, Symbol.X);
    }

    public MoveV2JSON(int i, Symbol symbol) {
        this.i = i;
        this.symbol = symbol;
    }


    public Symbol getSymbol() {
        return symbol;
    }

    public int getI() {
        return i;
    }
}
