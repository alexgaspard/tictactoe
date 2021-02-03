module game.domain {
    requires spring.context;
    exports me.alex.game.ports;
    exports me.alex.game.models;
    exports me.alex.game.exceptions;
}