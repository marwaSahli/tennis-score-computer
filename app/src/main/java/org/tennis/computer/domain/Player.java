package org.tennis.computer.domain;

import lombok.Getter;

@Getter
public class Player {
    private final String name;
    private boolean isLastShooter;
    private Score score;

    private Player(String name, Score score, boolean isLastShooter){
        this.name = name;
        this.score = score;
        this.isLastShooter = isLastShooter;
    }

    public static Player build(String name, Score score, boolean isLastShooter){
        return new Player(name, score, isLastShooter);
    }

    public static Player build(String name, Score score){
        return new Player(name, score,false);
    }

    public static Player buildFromData(Player player){
        return build(player.getName(), player.getScore(), player.isLastShooter());
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

    public void update(Score score){
        this.score = score;

    }

    public void update(boolean isLastShooter){
        this.isLastShooter = isLastShooter;

    }

}
