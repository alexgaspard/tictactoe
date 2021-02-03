package me.alex.game.controllers;


import me.alex.game.api.DeprecatedAPI;
import me.alex.game.exceptions.Busy;
import me.alex.game.exceptions.OutOfBoard;
import me.alex.game.models.MoveV1JSON;
import me.alex.game.ports.GameDrawer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static me.alex.game.config.WebConfig.APPLICATION_LATEST;

@RestController
@RequestMapping(MovesV1Controller.MOVES_PATH)
@ConditionalOnProperty(name = "api.v1.enabled", havingValue = "true")
public class MovesV1Controller {

    static final String MOVES_PATH = "/moves";
    static final String MOVES_VERSION = "application/vnd.alex.move-v1+json";

    private final GameDrawer gameDrawer;

    public MovesV1Controller(GameDrawer gameDrawer) {
        this.gameDrawer = gameDrawer;
    }

//    @GetMapping(value = MOVES_PATH, produces = {APPLICATION_MOVE_V1, WebConfig.APPLICATION_LATEST})
//    public Collection<MoveJSON> listOffers() {
//        return offersLister.listOffers().stream().map(this::convertToJSON).collect(Collectors.toList());
//    }
//
//    @GetMapping(value = MOVES_PATH + ID_PATH, produces = APPLICATION_MOVE_V1)
//    public MoveJSON getOffer(@PathVariable String id) {
//        return offerGetter.getOffer(id).map(this::convertToJSON)
//                .orElseThrow(NotFound::new);
//    }

    @DeleteMapping
    public ResponseEntity<Void> clearMoves() {
        gameDrawer.reset();
        return ResponseEntity.ok().build();
    }

    @Deprecated
    @DeprecatedAPI(location = "application/vnd.alex.move-v2+json")
    @PostMapping(consumes = {MOVES_VERSION, APPLICATION_LATEST})
    public ResponseEntity<String> createMove(@RequestBody @Valid MoveV1JSON move) {
        try {
            final boolean isWin = gameDrawer.play(move.getSymbol(), move.getX(), move.getY());
            return new ResponseEntity<>("{\"wins\": " + isWin + "}", HttpStatus.CREATED);
        } catch (Busy | OutOfBoard e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
