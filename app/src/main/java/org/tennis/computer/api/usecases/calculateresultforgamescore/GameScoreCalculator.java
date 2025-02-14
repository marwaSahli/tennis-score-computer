package org.tennis.computer.api.usecases.calculateresultforgamescore;

import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import org.tennis.computer.api.ports.ScoreCalculator;
import org.tennis.computer.application.CalculationHandler;
import org.tennis.computer.application.dto.GameScore;
import org.tennis.computer.domain.errors.DomainError;

@AllArgsConstructor
public class GameScoreCalculator implements ScoreCalculator<String, Either<Error, GameScore>> {

    private CalculationHandler<String, Either<DomainError, GameScore>> calculationHandler;

    @Override
    public Either<Error, GameScore> calculate(String gameScore){
        Either<DomainError, GameScore> result = calculationHandler.handle(gameScore);
        return  result.isLeft()? Either.left(new Error(result.getLeft().getMessage())) : Either.right(result.get());
    }
}
