package org.tennis.computer.application.dto;

import org.junit.jupiter.api.Test;
import org.tennis.computer.domain.Game;
import org.tennis.computer.domain.GameRound;
import org.tennis.computer.domain.Player;
import org.tennis.computer.domain.Score;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GameScoreTest {

    @Test
    void shouldCreateDtoFromDomainValue() {
        Game game = Game.build("AABBAA").get();

        game.update(GameRound.build(Player.build("A", Score.FIFTEEN),
                Player.build("B", Score.ZERO)));

        GameScore gameScore = GameScore.from(game);
        assertEquals("Party is on going .. no winner", gameScore.getGameResult());
        assertEquals("15", gameScore.getGameRounds().getLast().get(game.getGameRounds().getLast().getPlayerA().getName()));
        assertEquals("0", gameScore.getGameRounds().getLast().get(game.getGameRounds().getLast().getPlayerB().getName()));

        game.update(GameRound.build(Player.build("A", Score.THIRTY),
                Player.build("B", Score.ZERO)));
        gameScore = GameScore.from(game);
        assertEquals("Party is on going .. no winner", gameScore.getGameResult());
        assertEquals("30", gameScore.getGameRounds().getLast().get(game.getGameRounds().getLast().getPlayerA().getName()));
        assertEquals("0", gameScore.getGameRounds().getLast().get(game.getGameRounds().getLast().getPlayerB().getName()));

        game.update(GameRound.build(Player.build("A", Score.THIRTY),
                Player.build("B", Score.FIFTEEN)));
        gameScore = GameScore.from(game);
        assertEquals("Party is on going .. no winner", gameScore.getGameResult());
        assertEquals("30", gameScore.getGameRounds().getLast().get(game.getGameRounds().getLast().getPlayerA().getName()));
        assertEquals("15", gameScore.getGameRounds().getLast().get(game.getGameRounds().getLast().getPlayerB().getName()));

        game.update(GameRound.build(Player.build("A", Score.THIRTY),
                Player.build("B", Score.THIRTY)));
        gameScore = GameScore.from(game);
        assertEquals("Party is on going .. no winner", gameScore.getGameResult());
        assertEquals("30", gameScore.getGameRounds().getLast().get(game.getGameRounds().getLast().getPlayerA().getName()));
        assertEquals("30", gameScore.getGameRounds().getLast().get(game.getGameRounds().getLast().getPlayerB().getName()));


        game.update(GameRound.build(Player.build("A", Score.FOURTY),
                Player.build("B", Score.THIRTY)));
        gameScore = GameScore.from(game);
        assertEquals("Party is on going .. no winner", gameScore.getGameResult());
        assertEquals("40", gameScore.getGameRounds().getLast().get(game.getGameRounds().getLast().getPlayerA().getName()));
        assertEquals("30", gameScore.getGameRounds().getLast().get(game.getGameRounds().getLast().getPlayerB().getName()));


        game.update(GameRound.build(Player.build("A", Score.FOURTY),
                Player.build("B", Score.THIRTY)));
        gameScore = GameScore.from(game);
        assertEquals("Player A wins the game", gameScore.getGameResult());
        assertEquals("40", gameScore.getGameRounds().getLast().get(game.getGameRounds().getLast().getPlayerA().getName()));
        assertEquals("30", gameScore.getGameRounds().getLast().get(game.getGameRounds().getLast().getPlayerB().getName()));


        game.update(GameRound.build(Player.build("A", Score.FOURTY),
                Player.build("B", Score.FOURTY)));
        gameScore = GameScore.from(game);
        assertEquals("Player A wins the game", gameScore.getGameResult());
        assertEquals("40", gameScore.getGameRounds().getLast().get(game.getGameRounds().getLast().getPlayerA().getName()));
        assertEquals("40", gameScore.getGameRounds().getLast().get(game.getGameRounds().getLast().getPlayerB().getName()));


        game = Game.build("AABBAA").get();
        game.update(GameRound.build(Player.build("A", Score.FOURTY),
                Player.build("B", Score.FOURTY)));
        gameScore = GameScore.from(game);
        assertEquals("Match is in deuce", gameScore.getGameResult());
        assertEquals("40", gameScore.getGameRounds().getLast().get(game.getGameRounds().getLast().getPlayerA().getName()));
        assertEquals("40", gameScore.getGameRounds().getLast().get(game.getGameRounds().getLast().getPlayerB().getName()));

        game.update(GameRound.build(Player.build("A", Score.ADVANTAGE),
                Player.build("B", Score.FOURTY)));
        gameScore = GameScore.from(game);
        assertEquals("Player A has score advantage", gameScore.getGameResult());
        assertEquals("ADVANTAGE", gameScore.getGameRounds().getLast().get(game.getGameRounds().getLast().getPlayerA().getName()));
        assertEquals("40", gameScore.getGameRounds().getLast().get(game.getGameRounds().getLast().getPlayerB().getName()));

        game.update(GameRound.build(Player.build("A", Score.FOURTY),
                Player.build("B", Score.FOURTY)));

        gameScore = GameScore.from(game);
        assertEquals("Match is in deuce", gameScore.getGameResult());
        assertEquals("40", gameScore.getGameRounds().getLast().get(game.getGameRounds().getLast().getPlayerA().getName()));
        assertEquals("40", gameScore.getGameRounds().getLast().get(game.getGameRounds().getLast().getPlayerB().getName()));

        game.update(GameRound.build(Player.build("A", Score.FOURTY),
                Player.build("B", Score.ADVANTAGE)));
        gameScore = GameScore.from(game);
        assertEquals("Player B has score advantage", gameScore.getGameResult());
        assertEquals("40", gameScore.getGameRounds().getLast().get(game.getGameRounds().getLast().getPlayerA().getName()));
        assertEquals("ADVANTAGE", gameScore.getGameRounds().getLast().get(game.getGameRounds().getLast().getPlayerB().getName()));


        game.update(GameRound.build(Player.build("A", Score.FOURTY),
                Player.build("B", Score.ADVANTAGE)));
        gameScore = GameScore.from(game);
        assertEquals("Player B wins the game", gameScore.getGameResult());
        assertEquals("40", gameScore.getGameRounds().getLast().get(game.getGameRounds().getLast().getPlayerA().getName()));
        assertEquals("ADVANTAGE", gameScore.getGameRounds().getLast().get(game.getGameRounds().getLast().getPlayerB().getName()));
    }
}