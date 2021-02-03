package me.alex.game.ports;


import me.alex.game.models.Case;

public interface CaseStateSaver {

    void save(Case savedCase);

    void reset();
}
