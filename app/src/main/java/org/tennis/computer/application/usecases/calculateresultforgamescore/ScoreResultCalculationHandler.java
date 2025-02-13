package org.tennis.computer.application.usecases.calculateresultforgamescore;

import io.vavr.control.Either;
import org.tennis.computer.application.CalculationHandler;
import org.tennis.computer.application.dto.GameScore;
import org.tennis.computer.domain.Game;
import org.tennis.computer.domain.GameRound;
import org.tennis.computer.domain.Player;
import org.tennis.computer.domain.Score;
import org.tennis.computer.domain.errors.DomainError;

public class ScoreResultCalculationHandler implements CalculationHandler<String, Either<DomainError, GameScore>> {

    @Override
    public Either<DomainError, GameScore> handle(String scoreData) {
        var result = Game.build(scoreData);
        if(result.isLeft())
            return Either.left(result.getLeft());

        Game game = result.get();
        createGameScore(game);
        return Either.right(GameScore.from(game));
    }

    private void createGameScore(Game game){
        Player playerA = Player.build("A", Score.ZERO);
        Player playerB = Player.build("B", Score.ZERO);
        for (char point : game.getGameGlobalScore().toCharArray()) {
            switch (point){
                case 'A'  -> {
                    playerA.updateRoundScore(playerB);
                    if(playerB.getScore().equals(Score.ADVANTAGE))
                        playerB.update(Score.FOURTY);

                }
                case 'B' -> {
                    playerB.updateRoundScore(playerA);
                    if(playerA.getScore().equals(Score.ADVANTAGE))
                        playerA.update(Score.FOURTY);
                }
            }
            game.update(GameRound.build(
                    Player.buildFromData(playerA),
                    Player.buildFromData(playerB)));
        }
    }
}
