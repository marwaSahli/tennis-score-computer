package org.tennis.computer.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.tennis.computer.domain.Game;
import org.tennis.computer.domain.GameResult;
import org.tennis.computer.domain.Score;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class GameScore {
    private List<Map<String, String>> gameRounds;
    private String gameResult;

    public static GameScore from(Game game){
        List<Map<String, String>> gameRounds = game.getGameRounds().stream()
                .map(round ->
                        Map.of(round.getPlayerA().getName(), createScoreFromDomain(round.getPlayerA().getScore()),
                                round.getPlayerB().getName(), createScoreFromDomain(round.getPlayerB().getScore())
                        )
                )
                .toList();
        String gameResult = createGameResultFromDomain(game.getGameResult());
        return new GameScore(gameRounds,gameResult );
    }

    private static String createScoreFromDomain(Score score){
        return switch (score){
            case FIFTEEN -> "15";
            case THIRTY ->  "30";
            case FOURTY ->  "40";
            case ADVANTAGE -> "ADVANTAGE";
            case ZERO -> "0";
        };

    }

    private static String createGameResultFromDomain(GameResult gameResult){
        return switch (gameResult){
            case NO_WINNER_YET ->  "Party is on going .. no winner";
            case DEUCE -> "Match is in deuce";
            case A_ADVANTAGE -> "Player A has score advantage";
            case B_ADVANTAGE -> "Player B has score advantage";
            case A_WIN -> "Player A wins the game";
            case B_WIN -> "Player B wins the game";
        };
    }
}
