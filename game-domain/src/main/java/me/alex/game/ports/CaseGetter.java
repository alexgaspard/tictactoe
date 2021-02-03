package me.alex.game.ports;


import me.alex.game.models.Case;

public interface CaseGetter {

    Case getCase(int x, int y);
}
