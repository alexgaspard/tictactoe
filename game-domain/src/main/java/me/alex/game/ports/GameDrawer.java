package me.alex.game.ports;


import me.alex.game.exceptions.Busy;
import me.alex.game.exceptions.OutOfBoard;
import me.alex.game.models.Symbol;

public interface GameDrawer {

    void reset();

    boolean play(Symbol symbol, int x, int y) throws Busy, OutOfBoard;
}
