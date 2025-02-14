package org.tennis.computer.api.ports;

public interface ScoreCalculator<T, R> {
    R calculate(T scoreData);

}
