package org.tennis.computer.domain;

import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.tennis.computer.domain.errors.DomainError;
import org.tennis.computer.domain.errors.GameNotValid;
import java.util.stream.Stream;


public class GameTest {

    @ParameterizedTest
    @MethodSource("wrongGameValuesProvider")
    public void shouldRenderErrorWhenGameTourValueIsNotGood(String gameTour) {
        var game = Game.build(gameTour);
        Assertions.assertTrue(game.isLeft());
        Assertions.assertEquals(GameNotValid.class, game.getLeft().getClass());
    }

    @ParameterizedTest
    @MethodSource("goodGameValuesProvider")
    public void shouldRenderGameDomainValueWhenGameTourValueIsGood(String globalScore) {
        Either<DomainError, Game> game = Game.build(globalScore);
        Assertions.assertTrue(game.isRight());
        Assertions.assertEquals(globalScore, game.get().getGameGlobalScore());
        Assertions.assertEquals(Score.ZERO, game.get().getPlayerA().getScore());
        Assertions.assertEquals(Score.ZERO, game.get().getPlayerB().getScore());

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

    private static Stream<String> goodGameValuesProvider() {
        return Stream.of(
                "AABBA",
                "abAbAABB",
                "BBBBBB",
                "AAAAAAAA",
                "ababbba"
        );
    }
}