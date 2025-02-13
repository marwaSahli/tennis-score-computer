package org.tennis.computer.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameRoundTest {

    @Test
    void shouldBuildGameRound() {
        Player playerA = Player.build("Player A", Score.ZERO);
        Player playerB = Player.build("Player B", Score.ZERO);
        GameRound gameRound= GameRound.build(playerA, playerB);
        assertEquals(gameRound.getPlayerA(), playerA);
        assertEquals(gameRound.getPlayerB(), playerB);
    }
}