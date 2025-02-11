package org.tennis.computer.application;

public interface CalculationHandler<T, R> {
    public R handle(T scoreData);
}
