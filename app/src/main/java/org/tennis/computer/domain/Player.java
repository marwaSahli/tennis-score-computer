package org.tennis.computer.domain;

import lombok.Getter;

@Getter
public class Player {
    private String name;
    private Score score;

    private Player(String name, Score score){
        this.name = name;
        this.score = score;
    }

    public static Player build(String name, Score score){
        return new Player(name, score);
    }

    public void updateRoundScore(Player adversary){
        switch (this.score){
            case ZERO ->  this.score = Score.FIFTEEN;
            case FIFTEEN -> this.score = Score.THIRTY;
            case THIRTY ->  this.score = Score.FOURTY;
            case FOURTY -> {
                if(adversary.getScore().equals(Score.FOURTY))
                    this.score = Score.ADVANTAGE;
            }
            case ADVANTAGE -> {
                if(adversary.getScore().equals(Score.ADVANTAGE))
                    this.score = Score.FOURTY;
            }
        }
    }

    public void updateScore(Score score){
        this.score = score;

    }

}
