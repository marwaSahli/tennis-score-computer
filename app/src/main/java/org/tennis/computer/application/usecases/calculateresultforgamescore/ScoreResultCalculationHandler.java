package org.tennis.computer.application.usecases.calculateresultforgamescore;

import io.vavr.control.Either;
import org.tennis.computer.application.CalculationHandler;
import org.tennis.computer.application.dto.GameScore;
import org.tennis.computer.domain.Game;
import org.tennis.computer.domain.Score;
import org.tennis.computer.domain.errors.DomainError;

import java.util.ArrayList;
import java.util.List;

public class ScoreResultCalculationHandler implements CalculationHandler<String, Either<DomainError, List<GameScore>>> {
    @Override
    public Either<DomainError, List<GameScore>> handle(String scoreData) {
        var result = Game.build(scoreData);
        if(result.isLeft())
            return Either.left(result.getLeft());

        Game game = result.get();
        List<GameScore> gameSequencedScore = new ArrayList<>();
        for (char point : game.getGameGlobalScore().toCharArray()) {
            switch (point){
                case 'A'  -> {
                    game.getPlayerA().updateRoundScore(game.getPlayerB());
                    if(game.getPlayerB().getScore().equals(Score.ADVANTAGE))
                        game.getPlayerB().updateScore(Score.FOURTY);

                }
                case 'B' -> {
                    game.getPlayerB().updateRoundScore(game.getPlayerA());
                    if(game.getPlayerA().getScore().equals(Score.ADVANTAGE))
                        game.getPlayerA().updateScore(Score.FOURTY);
                }
            }
            gameSequencedScore.add(GameScore.from(game));
        }

        return Either.right(gameSequencedScore);
    }
}
