package me.alex.game;

import me.alex.game.exceptions.Busy;
import me.alex.game.exceptions.OutOfBoard;
import me.alex.game.models.Case;
import me.alex.game.models.Symbol;
import me.alex.game.ports.CaseGetter;
import me.alex.game.ports.CaseStateSaver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BoardTest {
    @Mock
    private CaseGetter getter;

    @Mock
    private CaseStateSaver saver;

    @InjectMocks
    private Board board;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void drawWhenCaseIsBusyShouldThrowException() {
        when(getter.getCase(anyInt(), anyInt())).thenReturn(new Case(0, 0, Symbol.O));
        assertThrows(Busy.class, () -> board.play(Symbol.O, 0, 0));
    }

    @Test
    public void drawWhenCompletesDiagonalShouldReturnWin() throws Busy, OutOfBoard {
        when(getter.getCase(anyInt(), anyInt())).thenReturn(new Case(0, 0, Symbol.X));
        when(getter.getCase(0, 0)).thenReturn(null).thenReturn(new Case(0, 0, Symbol.O));
        when(getter.getCase(1, 1)).thenReturn(new Case(1, 1, Symbol.O));
        when(getter.getCase(2, 2)).thenReturn(new Case(2, 2, Symbol.O));
        assertTrue(board.play(Symbol.O, 0, 0));
        verify(saver).save(new Case(0, 0, Symbol.O));
    }
}