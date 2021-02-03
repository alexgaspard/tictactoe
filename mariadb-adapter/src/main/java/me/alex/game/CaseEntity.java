package me.alex.game;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CaseEntity {

    @Id
    public final String id;
    public final int x;
    public final int y;
    public final String symbol;

    public CaseEntity() {
        this(0, 0, null);
    }

    public CaseEntity(int x, int y, String symbol) {
        this.id = Integer.toString(x) + y;
        this.x = x;
        this.y = y;
        this.symbol = symbol;
    }
}
