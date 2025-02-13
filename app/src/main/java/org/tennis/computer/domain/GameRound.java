package org.tennis.computer.domain;

import lombok.Getter;

@Getter
public class GameRound {
    private final Player playerA;
    private final Player playerB;

    private GameRound(Player playerA, Player playerB) {
        this.playerA = playerA;
        this.playerB = playerB;
    }

    public static  GameRound build(Player playerA, Player playerB){
        return new GameRound(playerA, playerB);
    }

}
