package org.tennis.computer.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.tennis.computer.domain.Game;
import org.tennis.computer.domain.Player;
import org.tennis.computer.domain.Score;

@Getter
@AllArgsConstructor
public class GameScore {
    String playerAscore;
    String playerBscore;

    public static GameScore from(Game game){
        return new GameScore(createScoreFromDomain(game.getPlayerA().getScore()),
                createScoreFromDomain(game.getPlayerB().getScore()));
    }

    private static String createScoreFromDomain(Score score){
        String scoreValue =  "0";
        switch (score){
            case FIFTEEN -> scoreValue =  "15";
            case THIRTY -> scoreValue =  "30";
            case FOURTY -> scoreValue =  "40";
            case ADVANTAGE -> scoreValue =  "ADVANTAGE";
        }

        return scoreValue;
    }
}
