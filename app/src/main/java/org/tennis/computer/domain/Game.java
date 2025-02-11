package org.tennis.computer.domain;

import io.vavr.control.Either;
import lombok.Getter;
import org.tennis.computer.domain.errors.DomainError;
import org.tennis.computer.domain.errors.GameNotValid;

@Getter
public class Game {
    private final String gameGlobalScore;
    private final Player playerA;
    private final Player playerB;

    private Game(String gameGlobalScore, Player playerA, Player playerB) {
        this.gameGlobalScore = gameGlobalScore.toUpperCase();
        this.playerA = playerA;
        this.playerB = playerB;
    }



    public static Either<DomainError, Game> build(String gameGlobalScore){
       return build(gameGlobalScore,
               Player.build("Player A", Score.ZERO),
               Player.build("Player B", Score.ZERO));
    }

    public static Either<DomainError, Game> build(String gameGlobalScore, Player playerA, Player playerB){
        if (!isValidGlobalScore(gameGlobalScore))
            return Either.left(new GameNotValid("Game party should have only letter A or B "));
        return Either.right(new Game(gameGlobalScore, playerA, playerB));
    }

    private static Boolean isValidGlobalScore(String gameGlobalScore){
       return gameGlobalScore != null && gameGlobalScore.toUpperCase().matches("[AB]+") ;
    }


}
