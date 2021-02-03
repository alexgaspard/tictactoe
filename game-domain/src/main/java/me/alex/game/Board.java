package me.alex.game;

import me.alex.game.exceptions.Busy;
import me.alex.game.exceptions.OutOfBoard;
import me.alex.game.models.Case;
import me.alex.game.models.Symbol;
import me.alex.game.ports.CaseGetter;
import me.alex.game.ports.CaseStateSaver;
import me.alex.game.ports.GameDrawer;
import org.springframework.stereotype.Service;

@Service
public class Board implements GameDrawer {

    public static final int GRID_LENGTH = 3;
    private final CaseGetter getter;
    private final CaseStateSaver saver;

    public Board(CaseGetter getter, CaseStateSaver saver) {
        this.getter = getter;
        this.saver = saver;
    }

    @Override
    public void reset() {
        saver.reset();
    }

    @Override
    public boolean play(Symbol symbol, int x, int y) throws Busy, OutOfBoard {
        if (x < 0 || x > 2 || y < 0 || y > 2) {
            throw new OutOfBoard();
        }
        Case previousCase = getter.getCase(x, y);
        if (previousCase != null) {
            throw new Busy();
        }
        saver.save(new Case(x, y, symbol));
        return doesWin(symbol, x, y);
    }

    private boolean doesWin(Symbol symbol, int x, int y) {
        //check col
        for (int i = 0; i < GRID_LENGTH; i++) {
            final Case retrieve = getter.getCase(x, i);
            if (retrieve == null) {
                break;
            }
            if (retrieve.symbol != symbol) {
                break;
            }
            if (i == GRID_LENGTH - 1) {
                return true;
            }
        }
        //check row
        for (int i = 0; i < GRID_LENGTH; i++) {
            final Case retrieve = getter.getCase(i, y);
            if (retrieve == null) {
                break;
            }
            if (retrieve.symbol != symbol) {
                break;
            }
            if (i == GRID_LENGTH - 1) {
                return true;
            }
        }
        //check diag
        if (x == y) {
            //we're on a diagonal
            for (int i = 0; i < GRID_LENGTH; i++) {
                final Case retrieve = getter.getCase(i, i);
                if (retrieve == null) {
                    break;
                }
                if (retrieve.symbol != symbol) {
                    break;
                }
                if (i == GRID_LENGTH - 1) {
                    return true;
                }
            }
        }
        //check anti diag
        if (x + y == GRID_LENGTH - 1) {
            for (int i = 0; i < GRID_LENGTH; i++) {
                final Case retrieve = getter.getCase(i, (GRID_LENGTH - 1) - i);
                if (retrieve == null) {
                    break;
                }
                if (retrieve.symbol != symbol) {
                    break;
                }
                if (i == GRID_LENGTH - 1) {
                    return true;
                }
            }
        }
        return false;
    }
}
