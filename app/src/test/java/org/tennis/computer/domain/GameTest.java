package org.tennis.computer.domain;

import io.vavr.control.Either;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.tennis.computer.domain.errors.DomainError;
import org.tennis.computer.domain.errors.GameNotValid;

import java.util.List;
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
        Assertions.assertEquals(globalScore.toUpperCase(), game.get().getGameGlobalScore());
        Assertions.assertEquals(0, game.get().getGameRounds().size());

        game = Game.build(globalScore, List.of());
        Assertions.assertTrue(game.isRight());
        Assertions.assertEquals(globalScore.toUpperCase(), game.get().getGameGlobalScore());
        Assertions.assertEquals(0, game.get().getGameRounds().size());

    }

    @Test
    public void shouldUpdateGame(){
        Either<DomainError, Game> game = Game.build("AABABA");

        Assertions.assertEquals(GameResult.NO_WINNER_YET, game.get().getGameResult());

        Player playerA = Player.build("player A", Score.FOURTY);
        Player playerB = Player.build("player B", Score.ZERO);
        GameRound gameRound = GameRound.build(playerA, playerB);
        game.get().update(gameRound);


        playerA.update(Score.FOURTY);
        playerA.update(true);
        playerB.update(false);
        game.get().update(gameRound);
        Assertions.assertEquals(GameResult.A_WIN, game.get().getGameResult());


        playerB.update(Score.FOURTY);
        playerA.update(false);
        playerB.update(true);
        game.get().update(gameRound);
        Assertions.assertEquals(GameResult.A_WIN, game.get().getGameResult());


        game = Game.build("ABBAB");
        gameRound = GameRound.build(playerA, playerB);
        playerB.update(Score.FOURTY);
        playerA.update(Score.FOURTY);
        game.get().update(gameRound);
        Assertions.assertEquals(GameResult.DEUCE, game.get().getGameResult());

        playerB.update(Score.ADVANTAGE);
        game.get().update(gameRound);
        Assertions.assertEquals(GameResult.B_ADVANTAGE, game.get().getGameResult());

        playerB.update(Score.ADVANTAGE);
        game.get().update(gameRound);
        Assertions.assertEquals(GameResult.B_WIN, game.get().getGameResult());

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