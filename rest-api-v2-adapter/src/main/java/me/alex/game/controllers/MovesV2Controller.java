package me.alex.game.controllers;


import me.alex.game.exceptions.Busy;
import me.alex.game.exceptions.OutOfBoard;
import me.alex.game.models.MoveV2JSON;
import me.alex.game.ports.GameDrawer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static me.alex.game.config.WebConfig.APPLICATION_LATEST;

@RestController
@RequestMapping(MovesV2Controller.MOVES_PATH)
@ConditionalOnProperty(name = "api.v2.enabled", havingValue = "true", matchIfMissing = true)
public class MovesV2Controller {

    static final String MOVES_PATH = "/moves";
    static final String MOVES_VERSION = "application/vnd.alex.move-v2+json";

    private final GameDrawer gameDrawer;

    public MovesV2Controller(GameDrawer gameDrawer) {
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

    @PostMapping(consumes = {MOVES_VERSION, APPLICATION_LATEST})
    public ResponseEntity<String> createMove(@RequestBody @Valid MoveV2JSON move) {
        try {
            final int x = move.getI() / 3;
            final int y = move.getI() % 3;
            final boolean isWin = gameDrawer.play(move.getSymbol(), x, y);
            return new ResponseEntity<>("{\"wins\": " + isWin + "}", HttpStatus.CREATED);
        } catch (Busy | OutOfBoard e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
