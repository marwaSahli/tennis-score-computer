package org.tennis.computer;

import io.vavr.control.Either;
import org.tennis.computer.api.ports.ScoreCalculator;
import org.tennis.computer.api.usecases.calculateresultforgamescore.GameScoreCalculator;
import org.tennis.computer.application.CalculationHandler;
import org.tennis.computer.application.dto.GameScore;
import org.tennis.computer.application.usecases.calculateresultforgamescore.ScoreResultCalculationHandler;
import org.tennis.computer.domain.errors.DomainError;

import java.util.Scanner;

public class Application {
    static ScoreCalculator<String, Either<Error, GameScore>> gameScoreCalculator;
    static CalculationHandler<String, Either<DomainError, GameScore>> calculationHandler;

    public static void main(String[] args) {

        initDependencies();
        System.out.println("*************************************************************************************************");
        System.out.println("Hello from tennis score calculator !");
        System.out.println("I will be able to give you a game score structured with z succession of letter 'A' or 'B'\n" +
                "A for player A and B for player B");
        System.out.println("Example AABBA means player A wins a ball , player A wins another ball , player B wins a ball etc ...");
        System.out.println("**************************************************************************************************");
        System.out.println("Now you know the rules, give a sentence containing letter A and B and click on Enter button :");
        Scanner scanner = new Scanner(System.in);
        String gameScore = scanner.nextLine();
        Either<Error, GameScore> result = gameScoreCalculator.calculate(gameScore);
        displayResults(result);

    }

    private static void displayResults(Either<Error, GameScore> result){
        if(result.isLeft()) {
            System.err.println("Ooops ! " + result.getLeft().getMessage());
            return;
        }

        result.get().getGameRounds().forEach(round -> {
            System.out.println("Player A : "+round.get("A")+ " / Player B : "+ round.get("B"));
        });
        System.out.println(result.get().getGameResult());
    }

    private static void  initDependencies() {
        calculationHandler = new ScoreResultCalculationHandler();
        gameScoreCalculator = new GameScoreCalculator(calculationHandler);
    }
}
