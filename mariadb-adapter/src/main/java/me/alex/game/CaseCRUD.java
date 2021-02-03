package me.alex.game;


import me.alex.game.models.Case;
import me.alex.game.models.Symbol;
import me.alex.game.ports.CaseGetter;
import me.alex.game.ports.CaseStateSaver;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CaseCRUD implements CaseGetter, CaseStateSaver {

    private final CaseRepository repository;
    private final Map<String, Symbol> dbToSymbol;
    private final Map<Symbol, String> symbolToDb;

    public CaseCRUD(CaseRepository repository) {
        this.repository = repository;
        dbToSymbol = new HashMap<>();
        dbToSymbol.put("X", Symbol.X);
        dbToSymbol.put("O", Symbol.O);
        symbolToDb = dbToSymbol.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }

    @Override
    public Case getCase(int x, int y) {
        return repository.findById(new CaseEntity(x, y, null).id)
                .map(c -> new Case(c.x, c.y, dbToSymbol.getOrDefault(c.symbol, null)))
                .orElse(null);
    }

    @Override
    public void save(Case savedCase) {
        repository.save(new CaseEntity(savedCase.x, savedCase.y, symbolToDb.getOrDefault(savedCase.symbol, null)));
    }

    @Override
    public void reset() {
        repository.deleteAll();
    }
}
