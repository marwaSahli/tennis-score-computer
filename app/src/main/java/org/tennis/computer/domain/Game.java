package org.tennis.computer.domain;

import io.vavr.control.Either;
import lombok.Getter;
import org.tennis.computer.domain.errors.DomainError;
import org.tennis.computer.domain.errors.GameNotValid;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Game {
    private final String gameGlobalScore;
    private final List<GameRound> gameRounds;
    private GameResult gameResult;

    private Game(String gameGlobalScore, List<GameRound> gameRounds) {
        this.gameGlobalScore = gameGlobalScore.toUpperCase();
        this.gameRounds = gameRounds;
        this.gameResult = GameResult.NO_WINNER_YET;
    }

    public static Either<DomainError, Game> build(String gameGlobalScore){
       return build(gameGlobalScore, new ArrayList<>());
    }

    public static Either<DomainError, Game> build(String gameGlobalScore, List<GameRound> gameRounds){
        if (!isValidGlobalScore(gameGlobalScore))
            return Either.left(new GameNotValid("Game party should have only letter A or B "));
        return Either.right(new Game(gameGlobalScore, gameRounds));
    }

    public void update(GameRound gameRound){
        this.gameRounds.add(gameRound);
        if (isResultDeuce()) {
            this.gameResult = GameResult.DEUCE;
            return;
        }
        if (isResultAdvantageForA()) {
            this.gameResult = GameResult.A_ADVANTAGE;
            return;
        }
        if (isResultAdvantageForB()) {
            this.gameResult = GameResult.B_ADVANTAGE;
            return;
        }
        if (isResultWinForA()) {
            this.gameResult = GameResult.A_WIN;
            return;
        }
        if (isResultWinForB())
            this.gameResult = GameResult.B_WIN;
    }

    private boolean isResultDeuce(){
        return this.gameRounds.getLast().getPlayerA().getScore().equals(Score.FOURTY)
                && this.gameResult != GameResult.A_WIN
                && this.gameResult != GameResult.B_WIN
                && this.gameRounds.getLast().getPlayerA().getScore()
                .equals(this.gameRounds.getLast().getPlayerB().getScore());
    }

    private boolean isResultAdvantageForA(){
        return this.gameRounds.getLast().getPlayerA().getScore().equals(Score.ADVANTAGE)
                && this.gameResult == GameResult.DEUCE;
    }

    private boolean isResultAdvantageForB(){
        return this.gameRounds.getLast().getPlayerB().getScore().equals(Score.ADVANTAGE)
                && this.gameResult == GameResult.DEUCE;
    }

    private boolean isResultWinForA(){

       return   (this.gameResult == GameResult.A_ADVANTAGE
               && this.gameRounds.getLast().getPlayerA().getScore().equals(Score.ADVANTAGE))
               ||
               (this.gameResult == GameResult.NO_WINNER_YET
               &&  this.gameRounds.size()>1
               && this.gameRounds.getLast().getPlayerA().isLastShooter()
               && this.gameRounds.getLast().getPlayerA().getScore().equals(Score.FOURTY)
               && this.gameRounds.get(this.gameRounds.size() - 2).getPlayerA().getScore().equals(Score.FOURTY)
               && !this.gameRounds.getLast().getPlayerB().getScore().equals(Score.ADVANTAGE));
    }

    private boolean isResultWinForB(){
        return   (this.gameResult == GameResult.B_ADVANTAGE
                && this.gameRounds.getLast().getPlayerB().getScore().equals(Score.ADVANTAGE))
                ||
                (this.gameResult == GameResult.NO_WINNER_YET
                        &&  this.gameRounds.size()>1
                        && this.gameRounds.getLast().getPlayerB().isLastShooter()
                        && this.gameRounds.getLast().getPlayerB().getScore().equals(Score.FOURTY)
                        && this.gameRounds.get(this.gameRounds.size() - 2).getPlayerB().getScore().equals(Score.FOURTY)
                        && !this.gameRounds.getLast().getPlayerA().getScore().equals(Score.ADVANTAGE));
    }

    private static Boolean isValidGlobalScore(String gameGlobalScore){
       return gameGlobalScore != null && gameGlobalScore.toUpperCase().matches("[AB]+") ;
    }
}
