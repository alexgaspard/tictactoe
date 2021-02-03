package me.alex.game.models;

import java.util.Objects;

public class Case {

    public final int x;
    public final int y;
    public final Symbol symbol;

    public Case(int x, int y, Symbol symbol) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Case aCase = (Case) o;
        return x == aCase.x && y == aCase.y && symbol == aCase.symbol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, symbol);
    }
}
