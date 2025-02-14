package org.tennis.computer.application;

public interface CalculationHandler<T, R> {
    R handle(T scoreData);
}
