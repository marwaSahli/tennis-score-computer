package org.tennis.computer.application.usecases.calculateresultforgamescore;

import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.tennis.computer.application.dto.GameScore;
import org.tennis.computer.domain.Game;
import org.tennis.computer.domain.Score;
import org.tennis.computer.domain.errors.DomainError;
import org.tennis.computer.domain.errors.GameNotValid;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


class ScoreResultCalculationHandlerTest {

    private  ScoreResultCalculationHandler scoreResultCalculationHandler;
    Either<DomainError, List<GameScore>> result;

    @BeforeEach
    void setUp() {
        scoreResultCalculationHandler = new ScoreResultCalculationHandler();
    }

    @ParameterizedTest
    @MethodSource("wrongGameValuesProvider")
    public void shouldRenderErrorWhenGameTourValueIsNotGood(String globalScore) {
        result = scoreResultCalculationHandler.handle(globalScore);
        Assertions.assertTrue(result.isLeft());
        Assertions.assertEquals(GameNotValid.class, result.getLeft().getClass());
    }

    @ParameterizedTest
    @MethodSource("goodGameValuesProvider")
    public void shouldRenderGameDomainValueWhenGameTourValueIsGood(String globalScore,
                                                                   List<Map<String, Score>> resultList) {
        result = scoreResultCalculationHandler.handle(globalScore);
        Assertions.assertTrue(result.isRight());
        for(int i = 0; i < result.get().size(); i++){
            Assertions.assertEquals(resultList.get(i).get("A"), result.get().get(i).getPlayerAscore());
            Assertions.assertEquals(resultList.get(i).get("B"), result.get().get(i).getPlayerBscore());
        }


    }

    private static Stream<String> wrongGameValuesProvider() {
        return Stream.of(
                null,
                "ABAAACDE",
                "YJUFHDFH",
                "DRFajlfkr",
                "Bhlipr"
        );
    }

    private static Stream<Arguments> goodGameValuesProvider() {
        return Stream.of(
                Arguments.of("AABBAA",
                        List.of(Map.of("A", "15", "B", "0"),
                                Map.of("A", "30", "B", "0"),
                                Map.of("A","30", "B","15"),
                                Map.of("A", "30", "B", "30"),
                                Map.of("A", "40", "B", "30"),
                                Map.of("A", "40", "B", "30")
                        )),
                Arguments.of("abAbABBABB",
                        List.of(Map.of("A", "15", "B", "0"),
                                Map.of("A", "15", "B", "15"),
                                Map.of("A", "30", "B", "15"),
                                Map.of("A", "30", "B", "30"),
                                Map.of("A", "40", "B", "30"),
                                Map.of("A","40", "B", "40"),
                                Map.of("A", "40", "B", "ADVANTAGE"),
                                Map.of("A", "40", "B","40"),
                                Map.of("A", "40", "B", "ADVANTAGE"),
                                Map.of("A", "40", "B", "ADVANTAGE")
                        ))
        );
    }
}