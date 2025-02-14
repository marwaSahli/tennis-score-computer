package org.tennis.computer.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void shouldBuildPlayerInstance() {
        Player player = Player.build("Player A", Score.ZERO);
        assertEquals("Player A", player.getName());
        assertEquals(Score.ZERO, player.getScore());
        assertFalse(player.isLastShooter());
    }

    @Test
    void shouldUpdatePlayerRoundScore() {
        Player player = Player.build("Player A", Score.ZERO);
        Player adversary = Player.build("Player B", Score.ZERO);
        player.updateRoundScore(adversary);
        assertEquals(Score.FIFTEEN,player.getScore());
        player.updateRoundScore(adversary);
        assertEquals(Score.THIRTY,player.getScore());
        player.updateRoundScore(adversary);
        assertEquals(Score.FOURTY,player.getScore());
        adversary = Player.build("Player B", Score.FOURTY);
        player.updateRoundScore(adversary);
        assertEquals(Score.ADVANTAGE,player.getScore());

    }

    @Test
    void shouldUpdatePlayerScore() {
        Player player = Player.build("Player A", Score.ZERO);
        player.update(Score.FIFTEEN);
        assertEquals(Score.FIFTEEN, player.getScore());
    }

    @Test
    void shouldUpdatePlayerShootingRound() {
        Player player = Player.build("Player A", Score.ZERO);
        player.update(true);
        assertTrue( player.isLastShooter());
    }

    @Test
    void shouldBuildFromData() {
        Player player = Player.build("Player A", Score.ZERO);
        Player buildedPlayer = Player.buildFromData(player);
        assertEquals(Score.ZERO, buildedPlayer.getScore());
        assertEquals("Player A", buildedPlayer.getName());
    }
}