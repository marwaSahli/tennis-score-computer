package org.tennis.computer.api.usecases.calculateresultforgamescore;

import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.tennis.computer.api.ports.ScoreCalculator;
import org.tennis.computer.application.CalculationHandler;
import org.tennis.computer.application.dto.GameScore;
import org.tennis.computer.application.usecases.calculateresultforgamescore.ScoreResultCalculationHandler;
import org.tennis.computer.domain.errors.DomainError;

class GameScoreCalculatorTest {

    @Test
    void shouldCalculateGameScore() {
        CalculationHandler<String, Either<DomainError, GameScore>> calculationHandler = new ScoreResultCalculationHandler();
        ScoreCalculator<String, Either<Error, GameScore>> gameScoreCalculator = new GameScoreCalculator(calculationHandler);
        Either<Error, GameScore> result = gameScoreCalculator.calculate("AXXABAA");
        Assertions.assertTrue(result.isLeft());

        result = gameScoreCalculator.calculate("AABAA");
        Assertions.assertTrue(result.isRight());
        Assertions.assertEquals("Player A wins the game", result.get().getGameResult());

    }
}